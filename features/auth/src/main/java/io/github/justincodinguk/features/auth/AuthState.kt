package io.github.justincodinguk.features.auth

import android.net.Uri
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.model.User

data class AuthState(
    val currentStatus: AuthStatus,
    val currentMode: AuthMode,
    val email: String,
    val password: String,
    val name: String,
    val profilePictureUri: Uri,
    val user: User?
) {
    companion object {
        fun empty(): AuthState {
            return AuthState(
                currentStatus = AuthStatus.Loading,
                currentMode = AuthMode.LOGIN,
                email = "",
                password = "",
                name = "",
                profilePictureUri = Uri.EMPTY,
                user = null
            )
        }
    }
}

enum class AuthMode {
    LOGIN,
    REGISTER
}
