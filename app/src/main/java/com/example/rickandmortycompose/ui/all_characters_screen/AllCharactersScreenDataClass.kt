package com.example.rickandmortycompose.ui.all_characters_screen

import androidx.paging.PagingData
import com.example.rickandmortycompose.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow

data class AllCharactersScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    var errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var characterList: MutableStateFlow<PagingData<Character>> = MutableStateFlow(PagingData.empty())
)
