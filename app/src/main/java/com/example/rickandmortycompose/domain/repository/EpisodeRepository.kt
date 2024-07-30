package com.example.rickandmortycompose.domain.repository

import com.example.rickandmortycompose.domain.model.Episode
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {
    fun getAllEpisodes(): Flow<Resource<List<Episode>>>
    fun getEpisodeById(id: String): Flow<Resource<Episode>>
}