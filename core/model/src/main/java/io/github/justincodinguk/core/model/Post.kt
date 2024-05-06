package io.github.justincodinguk.core.model

import android.net.Uri

data class Post(
    val id: String,
    val likeCount: Int,
    val author: User,
    val title: String,
    val body: String,
    val photoUrls: List<String>
) {
    companion object {
        fun empty() = Post(
            id = "",
            likeCount = 0,
            author = User.empty(),
            title = "",
            body = "",
            photoUrls = emptyList()
        )
    }
}
