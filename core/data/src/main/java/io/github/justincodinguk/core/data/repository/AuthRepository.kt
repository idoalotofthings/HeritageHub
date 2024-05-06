package io.github.justincodinguk.core.data.repository

import android.net.Uri
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val isSignedIn: Boolean

    suspend fun signIn(email: String, password: String) : Flow<AuthStatus>

    suspend fun googleSignIn(
        accountTokenId: String
    ) : Flow<AuthStatus>

    fun signOut()

    suspend fun getCurrentUser(): User?

    fun createUser(
        email: String,
        password: String,
        name: String,
        profileImageUri: Uri
    ): Flow<AuthStatus>

}