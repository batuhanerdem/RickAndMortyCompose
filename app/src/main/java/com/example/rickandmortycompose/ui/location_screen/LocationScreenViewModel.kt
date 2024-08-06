package com.example.rickandmortycompose.ui.location_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.repository.LocationRepository
import com.example.rickandmortycompose.utils.Resource
import com.example.rickandmortycompose.utils.clear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationScreenViewModel @Inject constructor(private val locationRepository: LocationRepository) :
    ViewModel() {
    val dataClass = LocationScreenDataClass()
    fun getAllLocations() {
        dataClass.loadingState.value = true
        locationRepository.getAllLocations().onEach { resource ->
            when (resource) {//make this generic in base
                is Resource.Error -> {
                    dataClass.loadingState.value = false
                    dataClass.errorState.value = resource.message ?: "Unknown Error"
                }

                is Resource.Success -> {
                    dataClass.errorState.clear()
                    resource.data?.let {
                        dataClass.loadingState.value = false
                        dataClass.locationList.value = it
                    }
                }
            }

        }.launchIn(viewModelScope)
    }
}