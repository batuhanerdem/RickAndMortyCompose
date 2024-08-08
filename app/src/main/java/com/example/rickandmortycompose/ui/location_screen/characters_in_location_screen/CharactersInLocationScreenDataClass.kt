package com.example.rickandmortycompose.ui.location_screen.characters_in_location_screen

import com.example.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

data class CharactersInLocationScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var characterList: MutableStateFlow<List<Character>> = MutableStateFlow(emptyList())
)
