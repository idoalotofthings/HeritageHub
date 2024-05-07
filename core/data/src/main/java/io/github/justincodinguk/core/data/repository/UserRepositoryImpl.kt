package io.github.justincodinguk.core.data.repository

import io.github.justincodinguk.core.database.dao.FollowingUsersDao
import io.github.justincodinguk.core.dev.toEntityUser
import io.github.justincodinguk.core.dev.toModelUser
import io.github.justincodinguk.core.firebase.di.UserService
import io.github.justincodinguk.core.firebase.firestore_service.FirestoreService
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    @UserService private val userFirestoreService: FirestoreService<User>,
    private val usersDao: FollowingUsersDao
) : UserRepository {

    override suspend fun updateUser(user: User) {
        userFirestoreService.updateDocument(user)
    }

    override suspend fun deleteUser(user: User) {
        userFirestoreService.deleteDocument(user)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFollowedUsers(): Flow<List<User>> {
        return usersDao.getAllUsers().mapLatest {
            it.map { e -> e.toModelUser() }
        }
    }

    override suspend fun unfollowUser(user: User) {
        usersDao.deleteUser(user.toEntityUser())
        // TODO: Update on firebase as well
    }

}