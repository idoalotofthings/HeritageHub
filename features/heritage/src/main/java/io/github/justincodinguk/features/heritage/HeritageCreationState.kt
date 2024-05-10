package io.github.justincodinguk.features.heritage

import io.github.justincodinguk.core.model.Heritage

data class HeritageCreationState(
    val username: String = "",
    val heritage: Heritage,
    val title: String,
    val generation: String,
) {
    companion object {
        fun empty() = HeritageCreationState(
            heritage = Heritage(),
            title = "",
            generation = "",
        )
    }
}