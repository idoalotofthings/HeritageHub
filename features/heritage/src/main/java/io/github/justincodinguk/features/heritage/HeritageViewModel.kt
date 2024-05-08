package io.github.justincodinguk.features.heritage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.justincodinguk.core.data.repository.UserRepository
import io.github.justincodinguk.core.model.Heritage
import io.github.justincodinguk.core.model.HeritageElement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeritageViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userHeritage = MutableStateFlow(Heritage())
    val userHeritage = _userHeritage.asStateFlow()

    private val _heritageCreationState = MutableStateFlow(HeritageCreationState.empty())
    val heritageCreationState = _heritageCreationState.asStateFlow()

    fun loadHeritageFor(userId: String) {
        viewModelScope.launch {
            val heritage = userRepository.getUser(userId).heritage
            _userHeritage.emit(heritage)
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