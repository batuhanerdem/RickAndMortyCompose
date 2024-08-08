package com.example.rickandmortycompose.ui.all_characters_screen.character_details

import com.example.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

data class CharacterDetailsScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var character: MutableStateFlow<Character?> = MutableStateFlow(null)
)
