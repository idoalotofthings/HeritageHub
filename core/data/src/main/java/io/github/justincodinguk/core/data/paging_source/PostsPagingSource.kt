package io.github.justincodinguk.core.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.justincodinguk.core.database.dao.PostsDao
import io.github.justincodinguk.core.dev.toModelPost
import io.github.justincodinguk.core.firebase.firestore.FirestoreService
import io.github.justincodinguk.core.model.Post

class PostsPagingSource(
    private val firestoreService: FirestoreService<Post>,
    private val postsDao: PostsDao,
    private val isFavMode: Boolean
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        if(!isFavMode) {
            val posts = firestoreService.getPagedDocuments(10, null)
            return LoadResult.Page(
                data = posts,
                prevKey = null,
                nextKey = null
            )
        } else {
            val posts = postsDao.getFavoritePosts().map { it.toModelPost() }
            return LoadResult.Page(
                data = posts,
                prevKey = null,
                nextKey = null
            )
        }
    }

}