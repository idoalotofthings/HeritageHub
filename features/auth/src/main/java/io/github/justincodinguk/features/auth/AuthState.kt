package io.github.justincodinguk.features.auth

import android.net.Uri
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
                currentStatus = AuthStatus.LOADING,
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

enum class AuthStatus {
    LOGGED_IN,
    NOT_LOGGED_IN,
    ERROR,
    LOADING
}

enum class AuthMode {
    LOGIN,
    REGISTER
}
