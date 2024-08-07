package com.example.rickandmortycompose.ui.character_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.domain.use_case.GetSeasonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterScreenViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {
    val dataClass = CharacterScreenDataClass()

    fun getAllCharacters() {
        dataClass.loadingState.value = true
        viewModelScope.launch {
            characterRepository.getAllCharacters().collect { pagingData ->
                val characterList = mutableListOf<Character>()
                Log.d("page", "getAllCharacters: $pagingData")
                pagingData.map { it ->
                    characterList.add(it)
                    Log.d("page", "getAllCharacters: $it")
                }
                dataClass.characterList.value = characterList
                dataClass.loadingState.value = false
            }
        }
    }

}