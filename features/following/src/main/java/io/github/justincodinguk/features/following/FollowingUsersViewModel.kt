package io.github.justincodinguk.features.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.data.repository.UserRepository
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FollowingUsersViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    val users = userRepository
        .getFollowedUsers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun unfollowUser(user: User) {
        viewModelScope.launch {
            val currentUserId = authRepository.getCurrentUser()!!.id
            userRepository.unfollowUser(currentUserId, user)
        }
    }

}