package io.github.justincodinguk.features.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.dev.AuthStatus
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow(AuthState.empty())
    val authState = _authState.asStateFlow()

    init {
        viewModelScope.launch {
            if (authRepository.isSignedIn) {
                val currentUser = authRepository.getCurrentUser()!!
                _authState.emit(
                    AuthState(
                        currentStatus = AuthStatus.Authenticated(currentUser),
                        user = currentUser,
                        currentMode = AuthMode.LOGIN,
                        email = currentUser.id,
                        password = "",
                        name = currentUser.name,
                        profilePictureUri = Uri.parse(currentUser.profileImage),
                    )
                )
            } else {
                _authState.emit(
                    AuthState(
                        currentStatus = AuthStatus.Unauthenticated,
                        user = null,
                        currentMode = AuthMode.LOGIN,
                        email = "",
                        password = "",
                        name = "",
                        profilePictureUri = Uri.EMPTY,
                    )
                )
            }

        }
    }

    fun login() {
        viewModelScope.launch {
            var user = User("","","")
            try {
                user = authRepository.signIn(_authState.value.email, _authState.value.password)
                _authState.emit(
                    AuthState(
                        currentStatus = AuthStatus.Loading,
                        user = null,
                        currentMode = AuthMode.LOGIN,
                        email = user.id,
                        password = _authState.value.password,
                        name = user.name,
                        profilePictureUri = Uri.EMPTY,
                    )
                )
            } catch (e: Exception) {
                _authState.emit(
                    AuthState.empty().copy(
                        currentStatus = AuthStatus.Error,
                        password = _authState.value.password,
                        email = _authState.value.email
                    )
                )
            } finally {
                _authState.emit(
                    AuthState(
                        currentStatus = AuthStatus.Loading,
                        user = user,
                        currentMode = AuthMode.LOGIN,
                        email = user.id,
                        password = _authState.value.password,
                        name = user.name,
                        profilePictureUri = Uri.parse(user.profileImage),
                    )
                )
            }
        }
    }

    fun register() {
        viewModelScope.launch {

                val user = authRepository.createUser(
                    _authState.value.email,
                    _authState.value.password,
                    _authState.value.name,
                    _authState.value.profilePictureUri
                )

                user.collect {
                    if(it is AuthStatus.Authenticated) {
                        _authState.emit(
                            AuthState(
                                currentStatus = AuthStatus.Authenticated(it.result),
                                user = it.result,
                                currentMode = AuthMode.REGISTER,
                                email = _authState.value.email,
                                password = _authState.value.password,
                                name = _authState.value.name,
                                profilePictureUri = _authState.value.profilePictureUri,
                            )
                        )
                    } else if(it is AuthStatus.Loading || it is AuthStatus.VerificationNeeded) {
                        _authState.emit(
                            _authState.value.copy(
                                currentStatus = it,
                            )
                        )
                    } else {
                        _authState.emit(
                            AuthState.empty().copy(
                                currentStatus = AuthStatus.Error,
                                password = _authState.value.password,
                                email = _authState.value.email,
                                currentMode = AuthMode.REGISTER
                            )
                        )
                    }
                }

        }
    }

    fun updateText(
        email: String? = null,
        password: String? = null,
        name: String? = null,
        profilePictureUri: Uri? = null
    ) {
        viewModelScope.launch {
            _authState.emit(
                _authState.value.copy(
                    email = email ?: _authState.value.email,
                    password = password ?: _authState.value.password,
                    name = name ?: _authState.value.name,
                    profilePictureUri = profilePictureUri ?: _authState.value.profilePictureUri,
                )
            )
        }
    }
}