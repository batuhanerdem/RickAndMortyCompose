package com.example.rickandmortycompose.data.service

import com.example.rickandmortycompose.domain.model.AllCharactersDTO
import com.example.rickandmortycompose.domain.model.Character
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CharacterService {
    @GET("${ServiceConstants.CHARACTER_ENDPOINT}/{id}")
    suspend fun getCharacterById(@Path("id") id: String): Response<Character>

    @GET(ServiceConstants.CHARACTER_ENDPOINT)
    suspend fun getAllCharacters(@Query("page") page: Int = 1): Response<AllCharactersDTO>

    @GET("${ServiceConstants.CHARACTER_ENDPOINT}/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") ids: String): Response<List<Character>>
}