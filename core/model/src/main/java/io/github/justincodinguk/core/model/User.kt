package io.github.justincodinguk.core.model

data class User(
    val id: String,
    val name: String,
    val profileImage: String,
    val heritage: Heritage,
    val followersId: List<String> = emptyList(),
    val tokenId: String = ""
) {
    companion object {
        fun empty() = User("", "", "", Heritage())
    }
}
