package co.com.ceiba.mobile.pruebadeingreso.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.ceiba.mobile.pruebadeingreso.model.Post
import co.com.ceiba.mobile.pruebadeingreso.model.User

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query(
        """
        SELECT id, userId, title, body FROM POST
        WHERE userId = :userId
        """
    )
    fun loadPosts(userId: Int): LiveData<List<Post>>
}