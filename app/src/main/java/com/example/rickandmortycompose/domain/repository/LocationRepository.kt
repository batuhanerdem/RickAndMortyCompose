package com.example.rickandmortycompose.domain.repository

import com.example.rickandmortycompose.domain.model.Location
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getAllLocations(): Flow<Resource<List<Location>>>
    fun getLocationById(id: String): Flow<Resource<Location>>
}