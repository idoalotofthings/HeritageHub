package io.github.justincodinguk.core.firebase.auth

import android.net.Uri
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.firebase.di.UserService
import io.github.justincodinguk.core.firebase.firestore_service.FirestoreService
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseAuthServiceImpl @Inject constructor(
    @UserService private val usersFirestoreService: FirestoreService<User>
) : FirebaseAuthService {
    private val auth = Firebase.auth

    override val isSignedIn = auth.currentUser != null

    override fun createUser(
        email: String,
        password: String,
        name: String,
        profilePictureUri: Uri
    ) = flow {

        emit(AuthStatus.Loading)
        try {

            auth.createUserWithEmailAndPassword(email, password).await()

            auth.currentUser?.sendEmailVerification()?.await()
            emit(AuthStatus.VerificationNeeded)
            var count = 0
            auth.currentUser?.isEmailVerified?.let {
                while(!auth.currentUser?.isEmailVerified!! && count<24){
                    count+=1
                    kotlinx.coroutines.delay(5000L)
                }

                if(auth.currentUser?.isEmailVerified!!) {
                    val user = User(
                        id = email,
                        name = name,
                        profileImage = profilePictureUri.toString()
                    )
                    usersFirestoreService.createDocument(user)
                    emit(AuthStatus.Authenticated(user))
                } else {
                    auth.currentUser?.delete()
                    emit(AuthStatus.Unauthenticated)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            auth.currentUser?.delete()
            emit(AuthStatus.Error)
        }
    }

    override suspend fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        return currentUser?.email?.let {
            usersFirestoreService.getDocumentById(it)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun signIn(email: String, password: String) = flow {
        emit(AuthStatus.Loading)
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = usersFirestoreService.getDocumentById(result.user?.email!!)
            emit(AuthStatus.Authenticated(user))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(AuthStatus.Error)
        }
    }

    override suspend fun googleSignIn(
        accountTokenId: String
    ) = flow {
        Log.d("AuthServiceImpl", "googleSignIn Loads")
        emit(AuthStatus.Loading)
        val credential = GoogleAuthProvider.getCredential(accountTokenId, null)
        try {
            val result = auth.signInWithCredential(credential).await()
            val user = usersFirestoreService.getDocumentById(result.user?.email!!)
            Log.d("AuthServiceImpl", "googleSignIn Success")
            emit(AuthStatus.Authenticated(user))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("AuthServiceImpl", "googleSignIn Error")
            emit(AuthStatus.Error)
        }
    }
}