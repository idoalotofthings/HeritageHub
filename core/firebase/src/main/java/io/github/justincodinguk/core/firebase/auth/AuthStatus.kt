package io.github.justincodinguk.core.firebase.auth

import io.github.justincodinguk.core.model.User

sealed class AuthStatus(val user: User? = null) {
    class Authenticated(val result: User) : AuthStatus(result)
    data object Unauthenticated : AuthStatus()
    data object Loading : AuthStatus()
}