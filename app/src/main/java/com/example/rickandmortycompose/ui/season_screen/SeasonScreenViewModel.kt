package com.example.rickandmortycompose.ui.season_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.model.Season
import com.example.rickandmortycompose.domain.use_case.GetSeasonsUseCase
import com.example.rickandmortycompose.utils.Resource
import com.example.rickandmortycompose.utils.clear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SeasonScreenViewModel @Inject constructor(private val getSeasonsUseCase: GetSeasonsUseCase) :
    ViewModel() {
    val dataClass = SeasonScreenDataClass()

    fun getSeasons() {
        dataClass.loadingState.value = true
        getSeasonsUseCase.execute().onEach { resource: Resource<List<Season>> ->
            dataClass.loadingState.value = false
            resource.message?.let {
                dataClass.errorState.value = it
                return@onEach
            }
            dataClass.errorState.clear()
            resource.data?.let {
                dataClass.seasonList.value = it
            }
        }.launchIn(viewModelScope)
    }
}