package co.com.ceiba.mobile.pruebadeingreso.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import co.com.ceiba.mobile.pruebadeingreso.db.UserDao
import co.com.ceiba.mobile.pruebadeingreso.db.UserRoomDatabase_Impl
import co.com.ceiba.mobile.pruebadeingreso.model.User
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.rest.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FakeRepositoryModule {

    @Provides
    @Singleton
    fun providerUserRepository(): UserRepository =
        object : UserRepository {
            val users = arrayListOf(
                User(id = 1,name = "eanne Graham",email = "incere@april.biz", phone = "-770-736-8031 x56442"),
                User(id = 2, name = "rvin Howell", email = "Shanna@melissa.tv", phone = "010-692-6593 x09125"),
                User(id = 3, name = "Clementine Bauch", email = "Nathan@yesenia.net", phone = "1-463-123-4447")
            )

            override fun callUsers() {
                TODO("Not yet implemented")
            }

            override fun getUsersFromDb(): LiveData<List<User>> {
                val usersLiveData = MutableLiveData<List<User>>()
                usersLiveData.value = users

                return usersLiveData
            }

            override fun getUsers(): MutableLiveData<List<User>> {
                val usersLiveData = MutableLiveData<List<User>>()
                usersLiveData.value = users

                return usersLiveData
            }

            override suspend fun insertUsers(users: List<User>) {
                TODO("Not yet implemented")
            }
        }
}