package io.github.justincodinguk.core.firebase.auth

import android.net.Uri
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthService {

    fun createUser(
        email: String,
        password: String,
        name: String,
        profilePictureUri: Uri
    ): Flow<io.github.justincodinguk.core.dev.AuthStatus>

    suspend fun signIn(
        email: String,
        password: String
    ): Result<User>

    fun signOut()

    suspend fun getCurrentUser() : User?

    val isSignedIn: Boolean

}