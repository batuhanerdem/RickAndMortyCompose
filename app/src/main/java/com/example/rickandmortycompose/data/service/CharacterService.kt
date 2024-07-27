package com.example.rickandmortycompose.data.service

import com.example.rickandmortycompose.domain.model.AllCharactersDTO
import com.example.rickandmortycompose.domain.model.ApiResponse
import com.example.rickandmortycompose.domain.model.Result
import retrofit2.Response
import retrofit2.http.GET


interface CharacterService {
    suspend fun getCharacter(id: String): Response<Result>

    @GET(ServiceConstants.CHARACTER_ENDPOINT)
    suspend fun getAllCharacters(): Response<ApiResponse<AllCharactersDTO>>


    private object ServiceConstants {
        const val CHARACTER_ENDPOINT = "character"
    }


}