package io.github.justincodinguk.core.firebase.firestore

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import io.github.justincodinguk.core.firebase.storage.FirebaseStorageService
import io.github.justincodinguk.core.model.Heritage
import io.github.justincodinguk.core.model.HeritageElement
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
        if (after != null) {
            query.startAfter(after)
        }
        val snapshot = query.get().await()
        return snapshot.toObjects(User::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    override suspend fun getDocumentById(id: String): User {
        val user = firestore.collection("/users").document(id).get().await()

        return User(
            id = id,
            name = user.getString("name") ?: "",
            profileImage = user.getString("profileImage") ?: "",
            heritage = Heritage()/*Heritage((user.get("heritage") as List<List<String>>).map {
                HeritageElement(it[0], it[1])
            })*/
        )
    }

    override suspend fun deleteDocument(document: User) {
        firestore.collection("/users").document(document.id).delete().await()
    }

    override suspend fun updateDocument(document: User) {
        firestore.collection("/users").document(document.id).set(document).await()
    }

    override suspend fun createDocument(document: User): String {
        val url = storageService.uploadProfileImage(document.id, Uri.parse(document.profileImage))
        firestore.collection("/users").document(document.id).set(document.copy(profileImage = url)).await()
        return document.id
    }

}