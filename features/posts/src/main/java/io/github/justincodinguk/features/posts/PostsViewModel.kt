package io.github.justincodinguk.features.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.PostsRepository
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    val posts = postsRepository
        .getPagedPosts().cachedIn(viewModelScope)


}