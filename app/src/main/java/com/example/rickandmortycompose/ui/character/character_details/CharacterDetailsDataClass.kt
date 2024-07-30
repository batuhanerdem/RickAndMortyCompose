package com.example.rickandmortycompose.ui.character.character_details

import com.example.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

data class CharacterDetailsDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val errorState: MutableStateFlow<String> = MutableStateFlow("testing78"),
    var character: MutableStateFlow<Character?> = MutableStateFlow(null)
)
