package io.github.justincodinguk.core.firebase.auth

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import io.github.justincodinguk.core.firebase.di.UserService
import io.github.justincodinguk.core.firebase.firestore_service.FirestoreService
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseAuthServiceImpl @Inject constructor(
    @UserService private val usersFirestoreService: FirestoreService<User>
) : FirebaseAuthService {
    private val auth = Firebase.auth

    override val isSignedIn = auth.currentUser != null

    override suspend fun createUser(
        email: String,
        password: String,
        name: String,
        profilePictureUri: Uri
    ) = flow {
        emit(AuthStatus.Loading)
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.currentUser?.sendEmailVerification()?.await()
            var count = 0
            auth.currentUser?.isEmailVerified?.let {
                while(!auth.currentUser?.isEmailVerified!! || count>=24){
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
            emit(AuthStatus.Unauthenticated)
        }
    }

    override suspend fun getCurrentUser(): User? {
        val currentUser = auth.currentUser
        return currentUser?.email?.let {
            usersFirestoreService.getDocumentById(it)
        }
    }

    override fun signOut() = auth.signOut()

    override suspend fun signIn(email: String, password: String) : Result<User> {
        try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val user = usersFirestoreService.getDocumentById(result.user?.email!!)
            return Result.success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.failure(e)
        }
    }
}