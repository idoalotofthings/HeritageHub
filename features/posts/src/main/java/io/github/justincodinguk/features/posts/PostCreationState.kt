package io.github.justincodinguk.features.posts

data class PostCreationState(
    val status: Status,
    val title: String,
    val body: String,
    val photos: List<String>
) {
    companion object {
        fun initial() = PostCreationState(
            status = Status.EDITING,
            title = "",
            body = "",
            photos = emptyList()
        )
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    EDITING
}
