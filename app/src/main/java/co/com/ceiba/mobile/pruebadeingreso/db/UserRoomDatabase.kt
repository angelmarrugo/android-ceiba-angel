package co.com.ceiba.mobile.pruebadeingreso.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import co.com.ceiba.mobile.pruebadeingreso.model.Post
import co.com.ceiba.mobile.pruebadeingreso.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [User::class, Post::class],version = 12)
abstract class UserRoomDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

}