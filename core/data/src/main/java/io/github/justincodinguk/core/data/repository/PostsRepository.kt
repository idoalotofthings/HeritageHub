package io.github.justincodinguk.core.data.repository

import androidx.paging.PagingData
import io.github.justincodinguk.core.model.Post
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    fun getPagedPosts() : Flow<PagingData<Post>>

    suspend fun getPostById(id: String) : Post

    suspend fun createPost(post: Post) : String

    suspend fun editPost(post: Post)

    suspend fun deletePost(post: Post)

    suspend fun savePost(post: Post, isFavorite: Boolean, isSelfAuthored: Boolean)

    fun getSelfAuthoredPosts() : Flow<List<Post>>

    fun getFavoritePostIds() : Flow<List<String>>
    fun getFavoritePosts() : Flow<PagingData<Post>>

}