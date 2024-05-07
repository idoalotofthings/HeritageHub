package io.github.justincodinguk.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import io.github.justincodinguk.core.database.dao.FollowingUsersDao
import io.github.justincodinguk.core.database.dao.PostsDao
import io.github.justincodinguk.core.database.entity.Post
import io.github.justincodinguk.core.database.entity.User

@Database(entities = [Post::class, User::class], version = 1)
abstract class HeritageHubDatabase : RoomDatabase() {
    abstract fun getPostsDao() : PostsDao
    abstract fun getFollowingUsersDao() : FollowingUsersDao
}