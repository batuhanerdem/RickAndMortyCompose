package com.example.rickandmortycompose.ui.character_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.domain.use_case.GetSeasonsUseCase
import com.example.rickandmortycompose.utils.Resource
import com.example.rickandmortycompose.utils.clear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharacterScreenViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {
    val dataClass = CharacterScreenDataClass()

    fun getAllCharacters() {
        dataClass.loadingState.value = true
        characterRepository.getAllCharacters().onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    dataClass.loadingState.value = false
                    dataClass.errorState.value = resource.message ?: "Unknown Error"
                }

                is Resource.Success -> {
                    dataClass.errorState.clear()
                    resource.data?.let {
                        dataClass.loadingState.value = false
                        dataClass.characterList.value = it
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}