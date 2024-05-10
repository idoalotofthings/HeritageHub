package io.github.justincodinguk.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.data.repository.PostsRepository
import io.github.justincodinguk.core.data.repository.UserRepository
import io.github.justincodinguk.core.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val postsRepository: PostsRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _post = MutableStateFlow(PostDetailsState(Post.empty(), false))
    val post = _post.asStateFlow()

    fun loadPost(id: String) {
        viewModelScope.launch {
            val result = postsRepository.getPostById(id)
            val currentUser = authRepository.getCurrentUser()!!.id
            _post.emit(PostDetailsState(result, currentUser in result.author.followersId))
        }
    }

    fun savePost() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()!!.id
            postsRepository.savePost(post.value.post, true, post.value.post.author.id == currentUser)
        }
    }

    fun followUser() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()!!.id
            userRepository.followUser(currentUser, post.value.post.author)
        }
    }
}