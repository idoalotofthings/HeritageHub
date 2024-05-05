package io.github.justincodinguk.core.data.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.justincodinguk.core.firebase.firestore_service.FirestoreService
import io.github.justincodinguk.core.model.Post

class PostsPagingSource(
    private val firestoreService: FirestoreService<Post>
) : PagingSource<Int, Post>() {
    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        val posts = firestoreService.getPagedDocuments(10, null)
        return LoadResult.Page(
            data = posts,
            prevKey = null,
            nextKey = null
        )
    }

}