package com.example.rickandmortycompose.ui.character

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
class CharacterScreenViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
    val dataClass = CharacterScreenDataClass()

    fun getAllCharacters() {
        characterRepository.getAllCharacters().onEach { resource ->
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
                        dataClass.characterList.value = it
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}