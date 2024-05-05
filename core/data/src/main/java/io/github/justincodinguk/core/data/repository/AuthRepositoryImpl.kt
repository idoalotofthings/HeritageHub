package io.github.justincodinguk.core.data.repository

import android.net.Uri
import io.github.justincodinguk.core.firebase.auth.FirebaseAuthService
import io.github.justincodinguk.core.model.User
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuthService,
) : AuthRepository {

    override val isSignedIn: Boolean
        get() = firebaseAuth.isSignedIn

    override suspend fun signIn(email: String, password: String): User {
        val result = firebaseAuth.signIn(email, password)
        if(result.isSuccess) {
            return result.getOrNull()!!
        } else {
            throw result.exceptionOrNull()!!
        }
    }

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
    ) = firebaseAuth.createUser(email,password,name,profileImageUri)
}