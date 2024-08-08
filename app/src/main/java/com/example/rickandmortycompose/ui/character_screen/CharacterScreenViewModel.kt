package com.example.rickandmortycompose.ui.character_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.domain.use_case.GetSeasonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterScreenViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getSeasonsUseCase: GetSeasonsUseCase
) : ViewModel() {
    val dataClass = CharacterScreenDataClass()

//    init {
//        getAllCharacters()
//    }

    fun getAllCharacters() {
        dataClass.loadingState.value = true
        viewModelScope.launch {
            characterRepository.getAllCharacters().distinctUntilChanged().cachedIn(viewModelScope)
                .collect {
                    dataClass.loadingState.value = false
                    dataClass.characterList.value = it
                }
        }

    }

}