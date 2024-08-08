package com.example.rickandmortycompose.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortycompose.domain.model.Location
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getAllLocations(): Flow<PagingData<Location>>
    fun getLocationById(id: String): Flow<Resource<Location>>
}