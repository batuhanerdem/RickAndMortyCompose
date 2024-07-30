package com.example.rickandmortycompose.data.service

import com.example.rickandmortycompose.domain.model.AllEpisodesDTO
import com.example.rickandmortycompose.domain.model.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodeService {
    @GET("${ServiceConstants.EPISODE_ENDPOINT}/{id}")
    suspend fun getEpisodeById(@Path("id") id: String): Response<Episode>

    @GET(ServiceConstants.EPISODE_ENDPOINT)
    suspend fun getAllEpisodes(): Response<AllEpisodesDTO>

}