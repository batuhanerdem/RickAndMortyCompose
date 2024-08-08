package com.example.rickandmortycompose.ui.all_characters_screen.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
    val dataClass = CharacterDetailsScreenDataClass()

    fun getCharacter(id: String) {
        characterRepository.getCharacterById(id).onEach { resource ->
            dataClass.loadingState.value = true
            when (resource) {
                is Resource.Error -> {
                    dataClass.loadingState.value = false
                    dataClass.errorState.value = resource.message ?: "Unknown Error"
                }
                is Resource.Success -> {
                    resource.data?.let {
                        dataClass.loadingState.value = false
                        dataClass.character.value = it
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}