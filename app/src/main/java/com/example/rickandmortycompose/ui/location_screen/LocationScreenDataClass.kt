package com.example.rickandmortycompose.ui.location_screen

import androidx.paging.PagingData
import com.example.rickandmortycompose.domain.model.Location
import kotlinx.coroutines.flow.MutableStateFlow

data class LocationScreenDataClass(
    val loadingState: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val errorState: MutableStateFlow<String> = MutableStateFlow(""),
    var locationList: MutableStateFlow<PagingData<Location>> = MutableStateFlow(PagingData.empty())
)
