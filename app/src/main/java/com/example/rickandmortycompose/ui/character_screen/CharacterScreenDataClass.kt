package com.example.rickandmortycompose.ui.character_screen

import com.example.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

data class CharacterScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var characterList: MutableStateFlow<List<Character>> = MutableStateFlow(emptyList())
)
