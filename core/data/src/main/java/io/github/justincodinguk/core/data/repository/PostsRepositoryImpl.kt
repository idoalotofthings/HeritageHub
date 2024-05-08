package io.github.justincodinguk.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import io.github.justincodinguk.core.data.paging_source.PostsPagingSource
import io.github.justincodinguk.core.database.dao.PostsDao
import io.github.justincodinguk.core.dev.toEntityPost
import io.github.justincodinguk.core.dev.toModelPost
import io.github.justincodinguk.core.firebase.di.PostService
import io.github.justincodinguk.core.firebase.firestore.FirestoreService
import io.github.justincodinguk.core.model.Post
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

internal class PostsRepositoryImpl @Inject constructor(
    @PostService private val firestoreService: FirestoreService<Post>,
    private val postsDao: PostsDao
) : PostsRepository {

    override fun getPagedPosts() = Pager(
        config = PagingConfig(pageSize = 20),
    ) { PostsPagingSource(firestoreService) }.flow

    override suspend fun createPost(post: Post) = firestoreService.createDocument(post)

    override suspend fun getPostById(id: String) = firestoreService.getDocumentById(id)

    override suspend fun editPost(post: Post) {
        firestoreService.updateDocument(post)
    }

    override suspend fun deletePost(post: Post) {
        firestoreService.deleteDocument(post)
    }

    override suspend fun savePost(post: Post, isFavorite: Boolean, isSelfAuthored: Boolean) {
        postsDao.insertPost(post.toEntityPost(isFavorite, isSelfAuthored))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavoritePosts(): Flow<List<Post>> {
        return postsDao.getFavoritePosts().mapLatest {
            it.map { e -> e.toModelPost()
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getSelfAuthoredPosts(): Flow<List<Post>> {
        return postsDao.getSelfPosts().mapLatest {
            it.map { e -> e.toModelPost() }
        }
    }

}