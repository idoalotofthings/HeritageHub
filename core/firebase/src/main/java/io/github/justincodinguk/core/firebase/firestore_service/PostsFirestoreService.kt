package io.github.justincodinguk.core.firebase.firestore_service

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import io.github.justincodinguk.core.firebase.di.UserService
import io.github.justincodinguk.core.firebase.storage.FirebaseStorageService
import io.github.justincodinguk.core.model.Post
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class PostsFirestoreService @Inject constructor(
    private val storageService: FirebaseStorageService,
    @UserService private val userFirestoreService: FirestoreService<User>
) : FirestoreService<Post> {
    private val firestore = Firebase.firestore

    override suspend fun getPagedDocuments(limit: Long, after: Post?): List<Post> {
        val collection = firestore.collection("/posts")
        val query = collection.limit(limit)
        if (after != null) {
            query.startAfter(after)
        }
        val snapshot = query.get().await()
        val posts = mutableListOf<Post>()
        for (doc: DocumentSnapshot in snapshot) {
            val userId = doc.getDocumentReference("author")
            val user = userFirestoreService.getDocumentById(userId!!.id)

            posts.add(
                Post(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    body = "",
                    likeCount = doc.getLong("likeCount")?.toInt() ?: 0,
                    photoUrls = listOf((doc.get("photos") as? List<String>)?.get(0) ?: ""),
                    author = user
                )
            )
        }
        return posts
    }

    override suspend fun getDocumentById(id: String): Post {
        val post = firestore.collection("/posts").document(id).get().await()
        return post.toObject(Post::class.java)!!
    }

    override suspend fun createDocument(document: Post) : String {
        val photos =
            storageService.uploadImages(document.id, document.photoUrls.map { Uri.parse(it) })
        val id = firestore.collection("/posts").document().id
        firestore.collection("/posts").document(id).set(document.copy(id = id, photoUrls = photos))
            .await()
        return id
    }

    override suspend fun deleteDocument(document: Post) {
        firestore.collection("/posts").document(document.id).delete().await()
    }

    override suspend fun updateDocument(document: Post) {
        firestore.collection("/posts").document(document.id).set(document).await()
    }

}