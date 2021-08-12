package co.com.ceiba.mobile.pruebadeingreso.rest

import co.com.ceiba.mobile.pruebadeingreso.model.Post
import co.com.ceiba.mobile.pruebadeingreso.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users/")
    fun getUsers() : Call<List<User>>

    @GET("posts/")
    fun getPosts(
        @Query("userId")userId: Int)
    : Call<List<Post>>
}