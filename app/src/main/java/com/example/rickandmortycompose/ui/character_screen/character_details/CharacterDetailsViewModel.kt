package com.example.rickandmortycompose.ui.character_screen.character_details

import android.util.Log
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
    val dataClass = CharacterDetailsDataClass()

    fun getCharacter(id: String) {
        characterRepository.getCharacterById(id).onEach { resource ->
            when (resource) {
                is Resource.Error -> {
                    dataClass.loadingState.value = false
                    dataClass.errorState.value = resource.message ?: "Unknown Error"
                }

                is Resource.Loading -> dataClass.loadingState.value = true
                is Resource.Success -> {
                    resource.data?.let {
                        Log.d("tag", "viewmodel test $it ")
                        dataClass.loadingState.value = false
                        dataClass.character.value = it
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}