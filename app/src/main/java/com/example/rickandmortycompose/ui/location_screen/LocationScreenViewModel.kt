package com.example.rickandmortycompose.ui.location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmortycompose.domain.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationScreenViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    val dataClass = LocationScreenDataClass()

    fun getAllLocations() {
        dataClass.loadingState.value = true
        locationRepository.getAllLocations().distinctUntilChanged().cachedIn(viewModelScope)
            .onEach {
                dataClass.loadingState.value = false
                dataClass.locationList.value = it

            }.launchIn(viewModelScope)
    }
}