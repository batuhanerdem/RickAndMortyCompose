package com.example.rickandmortycompose.data.service

import com.example.rickandmortycompose.domain.model.AllLocationsDTO
import com.example.rickandmortycompose.domain.model.Location
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationService {
    @GET(ServiceConstants.LOCATION_ENDPOINT)
    suspend fun getAllLocations(@Query("page") page: Int = 1): Response<AllLocationsDTO>

    @GET("${ServiceConstants.LOCATION_ENDPOINT}/{id}")
    suspend fun getLocationById(@Path("id") id: String): Response<Location>

}