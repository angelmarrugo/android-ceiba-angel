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

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): UserRoomDatabase {
            return  INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserRoomDatabase::class.java,
                    "json_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(UserDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                //~ return
                instance
            }
        }

        private class UserDatabaseCallback(
            private val scope: CoroutineScope
        ): RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch ( Dispatchers.IO ) {
                        populateDatabase(database.userDao())
                    }
                }
            }
        }
        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(userDao: UserDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            //...
        }
    }

}