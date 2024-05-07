package io.github.justincodinguk.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.justincodinguk.core.database.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface FollowingUsersDao {

    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

}