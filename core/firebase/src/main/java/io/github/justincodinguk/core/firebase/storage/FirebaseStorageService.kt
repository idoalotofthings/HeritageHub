package io.github.justincodinguk.core.firebase.storage

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

internal class FirebaseStorageService @Inject constructor() {

    private val storage = Firebase.storage.reference

    suspend fun uploadProfileImage(userId: String, imageUri: Uri) : String {
        val ref = storage.child("profile_images/$userId.jpg")
        return try {
            ref.putFile(imageUri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
    }

    suspend fun uploadImages(postId: String, imageUris: List<Uri>) : List<String> {
        val imageUrls = mutableListOf<String>()
        for(index in imageUris.indices) {
            val ref = storage.child("pics/$postId/$index.jpg")
            try {
                ref.putFile(imageUris[index]).await()
                imageUrls.add(ref.downloadUrl.await().toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return imageUrls
    }
}