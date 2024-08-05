package com.example.rickandmortycompose.ui.season_screen

import com.example.rickandmortycompose.domain.model.Season
import kotlinx.coroutines.flow.MutableStateFlow

data class SeasonScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var seasonList: MutableStateFlow<List<Season>> = MutableStateFlow(emptyList())
)
