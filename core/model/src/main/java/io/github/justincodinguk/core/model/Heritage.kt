package io.github.justincodinguk.core.model

data class Heritage(
    val heritageElements: List<HeritageElement> = emptyList()
)

data class HeritageElement(
    val title: String,
    val generation: String
)