package co.com.ceiba.mobile.pruebadeingreso.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.com.ceiba.mobile.pruebadeingreso.model.Post
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository): ViewModel() {

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
