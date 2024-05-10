package io.github.justincodinguk.features.detail

import io.github.justincodinguk.core.model.Post

data class PostDetailsState(
    val post: Post,
    val isAuthorFollowed: Boolean
)
