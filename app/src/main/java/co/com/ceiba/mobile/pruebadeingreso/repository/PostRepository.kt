package co.com.ceiba.mobile.pruebadeingreso.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.com.ceiba.mobile.pruebadeingreso.db.PostDao
import co.com.ceiba.mobile.pruebadeingreso.model.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val api: ApiService,
    private val postDao: PostDao
    ) {

    private var posts = MutableLiveData<List<Post>>()

    fun getPosts(): MutableLiveData<List<Post>> {
        return posts
    }

    fun reset() {
        posts = MutableLiveData<List<Post>>()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPosts(posts: List<Post>) {
        postDao.insertPosts(posts)
    }

    fun getPostsFromDb(userId: Int): LiveData<List<Post>> {
        return postDao.loadPosts(userId)
    }

    fun callPosts(userId: Int) {
        val call = api.getPosts(userId)
        call.enqueue(object : Callback<List<Post>> {

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                t.message?.let { Log.e(ApiService::class.java.name, it) }
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val postList = response.body()
                postList.let {
                    posts.value = it
                }
            }
        })
    }
}