package io.github.justincodinguk.features.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.data.repository.UserRepository
import io.github.justincodinguk.core.model.Heritage
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow(User.empty())
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()!!
            _user.emit(currentUser)
        }
    }

    fun updateUser(name: String, profile: String) {
        viewModelScope.launch {
            userRepository.updateUser(
                User(
                    _user.value.id,
                    name,
                    profile,
                    Heritage()
                )
            )
            val currentUser = authRepository.getCurrentUser()!!
            _user.emit(currentUser)
        }
    }

    fun signOut() {
        authRepository.signOut()
    }

}