package io.github.justincodinguk.core.firebase.firestore_service

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import io.github.justincodinguk.core.firebase.storage.FirebaseStorageService
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class UsersFirestoreService @Inject constructor(
    private val storageService: FirebaseStorageService
) : FirestoreService<User> {

    private val firestore = Firebase.firestore

    override suspend fun getPagedDocuments(limit: Long, after: User?): List<User> {
        val collection = firestore.collection("/posts")
        val query = collection.limit(limit)
        if(after != null) {
            query.startAfter(after)
        }
        val snapshot = query.get().await()
        return snapshot.toObjects(User::class.java)
    }

    override suspend fun getDocumentById(id: String): User {
        val user = firestore.collection("/users").document(id).get().await()
        return User(
            id = id,
            name = user.getString("name") ?: "",
            profileImage = user.getString("profileImage") ?: ""
        )
    }

    override suspend fun deleteDocument(document: User) {
        firestore.collection("/users").document(document.id).delete().await()
    }

    override suspend fun updateDocument(document: User) {
        firestore.collection("/users").document(document.id).set(document).await()
    }

    override suspend fun createDocument(document: User) {
        val url = storageService.uploadProfileImage(document.id, Uri.parse(document.profileImage))
        firestore.collection("/users").add(document.copy(profileImage = url)).await()
    }

}