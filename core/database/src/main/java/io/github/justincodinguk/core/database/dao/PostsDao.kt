package io.github.justincodinguk.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.justincodinguk.core.database.entity.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Insert
    suspend fun insertPost(post: Post)

    @Query("SELECT * FROM posts WHERE isFavorite = 1")
    suspend fun getFavoritePosts(): List<Post>

    @Query("SELECT * FROM posts WHERE isSelfCreated = 1")
    fun getSelfPosts(): Flow<List<Post>>

    @Query("SELECT id FROM posts WHERE isFavorite = 1")
    fun getFavoritePostIds(): Flow<List<String>>

    @Delete
    suspend fun deletePost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

}