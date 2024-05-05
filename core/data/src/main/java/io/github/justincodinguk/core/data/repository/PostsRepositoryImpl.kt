package io.github.justincodinguk.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.justincodinguk.core.data.paging_source.PostsPagingSource
import io.github.justincodinguk.core.firebase.di.PostService
import io.github.justincodinguk.core.firebase.firestore_service.FirestoreService
import io.github.justincodinguk.core.model.Post
import javax.inject.Inject

class PostsRepositoryImpl @Inject constructor(
    @PostService private val firestoreService: FirestoreService<Post>
) : PostsRepository {

    override fun getPagedPosts() = Pager(
        config = PagingConfig(pageSize = 20),
    ) { PostsPagingSource(firestoreService) }.flow

    override suspend fun addPost(post: Post) {
        firestoreService.createDocument(post)
    }

    override suspend fun editPost(post: Post) {
        firestoreService.updateDocument(post)
    }

    override suspend fun deletePost(post: Post) {
        firestoreService.deleteDocument(post)
    }

}