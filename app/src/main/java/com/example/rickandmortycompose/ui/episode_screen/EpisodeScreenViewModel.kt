package com.example.rickandmortycompose.ui.episode_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.repository.EpisodeRepository
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeScreenViewModel @Inject constructor(private val episodeRepository: EpisodeRepository) :
    ViewModel() {
    val dataClass = LocationScreenDataClass()
}