package io.github.justincodinguk.features.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.PostsRepository
import io.github.justincodinguk.core.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val postsRepository: PostsRepository
) : ViewModel() {

    private val _post = MutableStateFlow(Post.empty())
    val post = _post.asStateFlow()

    fun loadPost(id: String) {
        viewModelScope.launch {
            val result = postsRepository.getPostById(id)
            _post.emit(result)
        }
    }

}