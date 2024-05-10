package io.github.justincodinguk.features.heritage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.UserRepository
import io.github.justincodinguk.core.model.Heritage
import io.github.justincodinguk.core.model.HeritageElement
import io.github.justincodinguk.core.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeritageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userId = MutableStateFlow("")
    private val _userHeritage = MutableStateFlow(Heritage())
    val userHeritage = _userHeritage.asStateFlow()
    private val _username = MutableStateFlow(User.empty())
    val username = _username.asStateFlow()
    private val _heritageCreationState = MutableStateFlow(HeritageCreationState.empty())
    val heritageCreationState = _heritageCreationState.asStateFlow()

    fun loadHeritageFor(userId: String) {
        viewModelScope.launch {
            _userId.value = userId
            val user = userRepository.getUser(userId)
            _username.emit(user)
            _heritageCreationState.emit(
                HeritageCreationState(
                    heritage = _userHeritage.value,
                    title = _heritageCreationState.value.title,
                    generation = _heritageCreationState.value.generation
                )
            )
            _userHeritage.emit(user.heritage)
        }
    }

    fun addHeritageElement() {
        viewModelScope.launch {
            val newElements =
                _heritageCreationState.value.heritage.heritageElements + listOf(
                    HeritageElement(
                        _heritageCreationState.value.title,
                        _heritageCreationState.value.generation
                    )
                )
            _heritageCreationState.emit(
                _heritageCreationState.value.copy(
                    heritage = Heritage(
                        newElements
                    )
                )
            )
        }
    }

    fun saveHeritage() {
        viewModelScope.launch {
            userRepository.updateUser(
                userRepository.getUser(_userId.value).copy(heritage = heritageCreationState.value.heritage)
            )
            _userHeritage.emit(_heritageCreationState.value.heritage)
        }
    }

    fun updateText(title: String? = null, generation: String? = null) {
        viewModelScope.launch {
            _heritageCreationState.emit(
                _heritageCreationState.value.copy(
                    title = title ?: _heritageCreationState.value.title,
                    generation = generation ?: _heritageCreationState.value.generation
                )
            )
        }
    }
}