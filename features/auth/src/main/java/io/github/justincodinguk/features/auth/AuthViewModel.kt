package io.github.justincodinguk.features.auth

import android.net.Uri
import android.util.Log
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
            try {
                if (authRepository.isSignedIn) {
                    val currentUser = authRepository.getCurrentUser()!!
                    if(authRepository.isCurrentUserVerified()) {
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
                                currentStatus = AuthStatus.VerificationNeeded,
                                user = currentUser,
                                currentMode = AuthMode.LOGIN,
                                email = currentUser.id,
                                password = "",
                                name = currentUser.name,
                                profilePictureUri = Uri.parse(currentUser.profileImage),
                            )
                        )
                    }
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
            } catch(e: Exception) {
                e.printStackTrace()
                _authState.emit(
                    AuthState.empty().copy(
                        currentStatus = AuthStatus.Unauthenticated,
                        password = "",
                        email = "",
                    )
                )
            }
        }
    }

    fun login() {
        viewModelScope.launch {
            authRepository
                .signIn(_authState.value.email, _authState.value.password)
                .collect {

                    if (it is AuthStatus.Loading) {
                        _authState.emit(
                            AuthState(
                                currentStatus = AuthStatus.Loading,
                                user = null,
                                currentMode = AuthMode.LOGIN,
                                email = _authState.value.email,
                                password = _authState.value.password,
                                name = _authState.value.name,
                                profilePictureUri = Uri.EMPTY,
                            )
                        )
                    } else if (it is AuthStatus.Authenticated) {
                        _authState.emit(
                            AuthState(
                                currentStatus = it,
                                user = it.result,
                                currentMode = AuthMode.LOGIN,
                                email = it.result.id,
                                password = _authState.value.password,
                                name = it.result.name,
                                profilePictureUri = Uri.parse(it.result.profileImage),
                            )
                        )
                    } else if (it is AuthStatus.Unauthenticated) {
                        _authState.emit(
                            AuthState(
                                currentStatus = it,
                                user = null,
                                currentMode = AuthMode.LOGIN,
                                email = _authState.value.email,
                                password = _authState.value.password,
                                name = _authState.value.name,
                                profilePictureUri = _authState.value.profilePictureUri,
                            )
                        )
                    } else if (it is AuthStatus.Error) {
                        _authState.emit(
                            AuthState.empty().copy(
                                currentStatus = AuthStatus.Error,
                                password = _authState.value.password,
                                email = _authState.value.email
                            )
                        )
                    }
                }
        }
    }

    fun checkVerificationState() {
        Log.d("AuthVM", "Check Verification State")
        viewModelScope.launch {
            if(authRepository.isCurrentUserVerified()) {
                viewModelScope.launch {
                    authRepository.verifyUser(_authState.value.name, _authState.value.profilePictureUri)
                    val it = authRepository.getCurrentUser()!!
                    _authState.emit(
                        AuthState(
                            currentStatus = AuthStatus.Authenticated(it),
                            user = it,
                            currentMode = AuthMode.LOGIN,
                            email = it.id,
                            password = _authState.value.password,
                            name = it.name,
                            profilePictureUri = Uri.parse(it.profileImage),
                        )
                    )
                }
            }
        }
    }

    fun deleteUser() {
        Log.d("AuthVM", "Delete User")

        viewModelScope.launch {
            authRepository.deleteUser()
            _authState.emit(
                AuthState.empty().copy(
                    currentStatus = AuthStatus.Unauthenticated,
                    user = null,
                    currentMode = AuthMode.LOGIN,
                    email = "",
                    password = "",
                )
            )
        }
    }

    fun register() {
        Log.d("AuthVM", "Register")
        viewModelScope.launch {
            val user = authRepository.createUser(
                _authState.value.email,
                _authState.value.password,
                _authState.value.name,
                _authState.value.profilePictureUri
            )

            user.collect {
                if (it is AuthStatus.Authenticated) {
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
                } else if (it is AuthStatus.Loading || it is AuthStatus.VerificationNeeded) {
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

    fun signInWithGoogle(accountId: String) {
        viewModelScope.launch {
            authRepository.googleSignIn(accountId).collect {
                if (it is AuthStatus.Authenticated) {
                    _authState.emit(
                        _authState.value.copy(
                            currentStatus = AuthStatus.Authenticated(it.result),
                            user = it.result,
                            currentMode = AuthMode.LOGIN,
                        )
                    )
                } else if(it is AuthStatus.Error) {
                    _authState.emit(
                        AuthState.empty().copy(
                            currentStatus = AuthStatus.Error,
                            currentMode = AuthMode.LOGIN
                        )
                    )
                } else if(it is AuthStatus.Loading) {
                    _authState.emit(
                        AuthState.empty().copy(
                            currentStatus = AuthStatus.Loading,
                            currentMode = AuthMode.LOGIN
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