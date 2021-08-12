package co.com.ceiba.mobile.pruebadeingreso

import android.app.Application
import co.com.ceiba.mobile.pruebadeingreso.db.UserRoomDatabase
import co.com.ceiba.mobile.pruebadeingreso.repository.PostRepository
import co.com.ceiba.mobile.pruebadeingreso.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App: Application(){

    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    private val database by lazy { UserRoomDatabase.getDatabase(this, applicationScope) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val postRepository by lazy { PostRepository(database.postDao()) }

}