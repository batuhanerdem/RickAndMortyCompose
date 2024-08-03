package com.example.rickandmortycompose.ui.location_screen.characters_in_location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CharactersInLocationScreenViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
    val dataClass = CharactersInLocationScreenDataClass()

    fun getCharacters(idList: List<String>) {
        characterRepository.getMultipleCharacters(idList.getIdListFromUrlList().toString())
            .onEach { resource ->
                dataClass.loadingState.value = true
                when (resource) {//make this generic in base

                    is Resource.Error -> {
                        dataClass.loadingState.value = false
                        dataClass.errorState.value = resource.message ?: "Unknown Error"
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            dataClass.loadingState.value = false
                            dataClass.characterList.value = it
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun List<String>.getIdListFromUrlList(): List<String> {
        return this.map {
            it.substringAfterLast("/")
        }
    }
}