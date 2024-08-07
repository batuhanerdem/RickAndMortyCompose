package com.example.rickandmortycompose.data.repository

import android.util.Log
import com.example.rickandmortycompose.data.service.LocationService
import com.example.rickandmortycompose.domain.model.Location
import com.example.rickandmortycompose.domain.repository.LocationRepository
import com.example.rickandmortycompose.utils.ERROR
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class LocationRepositoryImpl @Inject constructor(private val service: LocationService) :
    LocationRepository {
    override fun getAllLocations(): Flow<Resource<List<Location>>> = flow {
        try {
            val dto = service.getAllLocations().body()!!
            emit(Resource.Success(dto.results))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(ERROR, "getAllLocations: ${e.localizedMessage}")
        }
    }

    override fun getLocationById(id: String): Flow<Resource<Location>> = flow {
        try {
            val location = service.getLocationById(id).body()!!
            emit(Resource.Success(location))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(ERROR, "getAllLocations: ${e.localizedMessage}")
        }
    }

}