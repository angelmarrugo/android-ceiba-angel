package co.com.ceiba.mobile.pruebadeingreso.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.com.ceiba.mobile.pruebadeingreso.db.UserDao
import co.com.ceiba.mobile.pruebadeingreso.model.User
import co.com.ceiba.mobile.pruebadeingreso.rest.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

interface UserRepository {
    fun callUsers()
    fun getUsersFromDb(): LiveData<List<User>>
    fun getUsers(): MutableLiveData<List<User>>
    suspend fun insertUsers(users: List<User>)
}

class UserRepositoryImp @Inject constructor(
    private val api: ApiService,
    private val userDao: UserDao
    ): UserRepository {

    private var users = MutableLiveData<List<User>>()

    override fun getUsers(): MutableLiveData<List<User>> {
        return users
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun insertUsers(users: List<User>) {
        userDao.insertUsers(users)
    }

    override fun getUsersFromDb(): LiveData<List<User>> {
        return userDao.loadUsers()
    }

     override fun callUsers() {
        val call = api.getUsers()
        call.enqueue(object : Callback<List<User>> {

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                t.message?.let { Log.e(ApiService::class.java.name, it) }
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val userList = response.body()
                userList.let {
                    users.value = it
                }
            }
        })
    }
}