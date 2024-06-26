package io.github.justincodinguk.core.data.repository

import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

    fun getFollowedUsers(): Flow<List<User>>

    suspend fun unfollowUser(currentId: String, user: User)

    suspend fun getUser(userId: String): User

    suspend fun followUser(currentId: String, user: User)

}