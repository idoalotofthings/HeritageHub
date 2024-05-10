package io.github.justincodinguk.core.data.repository

import android.net.Uri
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.firebase.auth.FirebaseAuthService
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuthService,
) : AuthRepository {

    override val isSignedIn: Boolean
        get() = firebaseAuth.isSignedIn

    override fun deleteUser() {
        firebaseAuth.deleteUser()
    }

    override suspend fun verifyUser(name: String, profileImageUri: Uri) {
        firebaseAuth.verifyUser(name, profileImageUri)
    }

    override suspend fun signIn(email: String, password: String) =
        firebaseAuth.signIn(email, password)

    override suspend fun googleSignIn(
        accountTokenId: String,
        mail: String,
        name: String,
        profileImageUri: Uri
    ): Flow<AuthStatus> = firebaseAuth.googleSignIn(
        profilePictureUri = profileImageUri,
        accountTokenId = accountTokenId,
        name = name,
        mail = mail
    )


    override fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun getCurrentUser(): User? {
        return firebaseAuth.getCurrentUser()
    }

    override fun createUser(
        email: String,
        password: String,
        name: String,
        profileImageUri: Uri
    ) = firebaseAuth.createUser(email, password, name, profileImageUri)

    override suspend fun isCurrentUserVerified(): Boolean {
        return firebaseAuth.isVerified()
    }
}