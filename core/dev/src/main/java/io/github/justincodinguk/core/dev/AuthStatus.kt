package io.github.justincodinguk.core.dev

import io.github.justincodinguk.core.model.User

sealed class AuthStatus(val user: User? = null) {
    class Authenticated(val result: User) : AuthStatus(result)
    data object Unauthenticated : AuthStatus()
    data object Error : AuthStatus()
    data object Loading : AuthStatus()
    data object VerificationNeeded : AuthStatus()
}