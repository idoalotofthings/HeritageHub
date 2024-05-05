package io.github.justincodinguk.core.data.repository

import android.net.Uri
import io.github.justincodinguk.core.model.User

interface AuthRepository {

    val isSignedIn: Boolean

    suspend fun signIn(email: String, password: String) : User

    fun signOut()

    suspend fun getCurrentUser(): User?

    suspend fun createUser(
        email: String,
        password: String,
        name: String,
        profileImageUri: Uri
    ): User

}