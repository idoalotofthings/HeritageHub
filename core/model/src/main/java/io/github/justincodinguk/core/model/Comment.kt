package io.github.justincodinguk.core.model

data class Comment(
    val id: String,
    val body: String,
    val author: User
) {
    companion object {
        fun empty() = Comment("", "", User.empty())
    }
}
