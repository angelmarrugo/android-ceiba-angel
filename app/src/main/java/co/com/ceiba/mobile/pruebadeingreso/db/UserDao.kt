package co.com.ceiba.mobile.pruebadeingreso.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.ceiba.mobile.pruebadeingreso.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)

    @Query(
        """
        SELECT id, name, email, phone FROM USER
        ORDER BY name DESC"""
    )
    fun loadUsers(): LiveData<List<User>>
}