package com.example.rickandmortycompose.data.service

import com.example.rickandmortycompose.domain.model.AllLocationsDTO
import com.example.rickandmortycompose.domain.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface LocationService {
    @GET(ServiceConstants.LOCATION_ENDPOINT)
    suspend fun getAllLocations(): Response<AllLocationsDTO>

    @GET("${ServiceConstants.LOCATION_ENDPOINT}/{id}")
    suspend fun getLocationById(@Path("id") id: String): Response<Location>

}