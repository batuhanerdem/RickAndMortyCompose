package com.example.rickandmortycompose.ui.episode_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.use_case.GetMultipleEpisodesUseCase
import com.example.rickandmortycompose.utils.clear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EpisodeScreenViewModel @Inject constructor(private val getMultipleEpisodesUseCase: GetMultipleEpisodesUseCase) :
    ViewModel() {
    val dataClass = EpisodeScreenDataClass()

    fun getEpisodes(episodeIdPair: Pair<Int, Int>) {
        dataClass.loadingState.value = true
        getMultipleEpisodesUseCase.execute(episodeIdPair).onEach { resource ->
            dataClass.loadingState.value = false
            resource.message?.let {
                dataClass.errorState.value = it
                return@onEach
            }
            resource.data?.let {
                dataClass.errorState.clear()
                dataClass.episodeList.value = it
                Log.d("episodes", "getEpisodes: ${it.count()}\n\n$it ")
            }

        }.launchIn(viewModelScope)
    }
}