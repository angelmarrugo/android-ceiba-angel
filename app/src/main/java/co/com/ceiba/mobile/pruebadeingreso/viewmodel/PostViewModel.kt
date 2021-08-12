package co.com.ceiba.mobile.pruebadeingreso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.model.Post
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository): ViewModel() {

    fun getPosts(): LiveData<List<Post>> {
        return repository.getPosts()
    }

    fun callPosts(userId: Int){
        repository.callPosts(userId)
    }

    fun getPostsFromDb(userId: Int): LiveData<List<Post>> {
        return repository.getPostsFromDb(userId)
    }

    fun savePosts(posts: List<Post>) = viewModelScope.launch {
        repository.insertPosts(posts)
    }

    override fun onCleared() {
        Log.d(PostViewModel::class.simpleName,"onCleared")
        repository.reset()
        super.onCleared()
    }
}

class PostViewModelFactory(private val repository: PostRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}