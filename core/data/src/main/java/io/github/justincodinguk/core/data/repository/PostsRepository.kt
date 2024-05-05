package io.github.justincodinguk.core.data.repository

import androidx.paging.PagingData
import io.github.justincodinguk.core.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getPagedPosts() : Flow<PagingData<Post>>

    suspend fun addPost(post: Post)

    suspend fun editPost(post: Post)

    suspend fun deletePost(post: Post)

}