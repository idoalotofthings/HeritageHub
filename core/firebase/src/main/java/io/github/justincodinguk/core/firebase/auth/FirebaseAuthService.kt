package io.github.justincodinguk.core.firebase.auth

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthService {

    fun createUser(
        email: String,
        password: String,
        name: String,
        profilePictureUri: Uri
    ): Flow<AuthStatus>

    suspend fun signIn(
        email: String,
        password: String
    ): Flow<AuthStatus>

    suspend fun googleSignIn(
        accountTokenId: String
    ): Flow<AuthStatus>

    fun signOut()

    suspend fun getCurrentUser() : User?

    val isSignedIn: Boolean

}