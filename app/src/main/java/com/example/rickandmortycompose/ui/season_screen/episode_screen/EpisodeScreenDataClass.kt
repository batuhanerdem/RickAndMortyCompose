package com.example.rickandmortycompose.ui.season_screen.episode_screen

import com.example.rickandmortycompose.domain.model.Episode
import kotlinx.coroutines.flow.MutableStateFlow

data class EpisodeScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var episodeList: MutableStateFlow<List<Episode>> = MutableStateFlow(emptyList())
)
