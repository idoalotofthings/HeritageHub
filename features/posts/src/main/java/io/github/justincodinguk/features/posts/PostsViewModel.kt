package io.github.justincodinguk.features.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.data.repository.PostsRepository
import io.github.justincodinguk.core.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val authRepository: AuthRepository,
) : ViewModel() {

    val posts = postsRepository
        .getPagedPosts().cachedIn(viewModelScope)

    val selfAuthoredPosts = postsRepository
        .getSelfAuthoredPosts().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _postCreationState = MutableStateFlow(PostCreationState.initial())
    val postCreationState = _postCreationState.asStateFlow()

    fun createNewPost() {
        viewModelScope.launch {
            val post = Post(
                id = "",
                title = postCreationState.value.title,
                body = postCreationState.value.body,
                likeCount = 0,
                author = authRepository.getCurrentUser()!!,
                photoUrls = postCreationState.value.photos
            )
            val id = postsRepository.createPost(post)
            postsRepository.savePost(post.copy(id = id), isFavorite = false, isSelfAuthored = true)
            _postCreationState.emit(PostCreationState.initial().copy(status = Status.SUCCESS))

        }
    }

    fun likePost(id: String) {
        viewModelScope.launch {
            val post = postsRepository.getPostById(id)
            postsRepository.editPost(
                post.copy(likeCount = post.likeCount+1)
            )
        }
    }

    fun savePost(id: String) {
        viewModelScope.launch {
            val post = postsRepository.getPostById(id)
            postsRepository.savePost(post, isFavorite = true, isSelfAuthored = false)
        }
    }

    fun updatePostCreationState(
        title: String? = null,
        body: String? = null,
        photos: List<String> = emptyList()
    ) {
        viewModelScope.launch {
            _postCreationState.emit(
                _postCreationState.value.copy(
                    title = title ?: _postCreationState.value.title,
                    body = body ?: _postCreationState.value.body,
                    photos = photos.ifEmpty { _postCreationState.value.photos }
                )
            )
        }
    }
}