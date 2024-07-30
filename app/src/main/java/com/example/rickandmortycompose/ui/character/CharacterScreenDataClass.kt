package com.example.rickandmortycompose.ui.character

import com.example.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

data class CharacterScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val errorState: MutableStateFlow<String> = MutableStateFlow("testing"),
    var characterList: MutableStateFlow<List<Character>> = MutableStateFlow(emptyList())
)
