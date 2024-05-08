package io.github.justincodinguk.core.firebase.firestore


interface FirestoreService<T> {

    suspend fun getPagedDocuments(limit: Long, after: T?): List<T>

    suspend fun getDocumentById(id: String): T

    suspend fun createDocument(document: T) : String

    suspend fun updateDocument(document: T)

    suspend fun deleteDocument(document: T)

}