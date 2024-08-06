package com.example.rickandmortycompose.data.repository

import android.util.Log
import com.example.rickandmortycompose.data.service.EpisodeService
import com.example.rickandmortycompose.domain.model.Episode
import com.example.rickandmortycompose.domain.repository.EpisodeRepository
import com.example.rickandmortycompose.utils.Resource
import com.example.rickandmortycompose.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(private val service: EpisodeService) :
    EpisodeRepository {
    override fun getAllEpisodes(page: Int): Flow<Resource<List<Episode>>> = flow {
        try {
            val list = service.getAllEpisodes(page).body()!!.results
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(TAG, "getAllCharacters: ${e.localizedMessage}")
        }

    }

    override fun getPageCount(): Flow<Resource<Int>> = flow {
        try {
            val page = service.getAllEpisodes().body()!!.info.pages
            emit(Resource.Success(page))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }

    }

    override fun getMultipleEpisodes(episodeListString: String): Flow<Resource<List<Episode>>> =
        flow {
            try {
                val episodeList = service.getMultipleEpisodes(episodeListString).body()!!
                emit(Resource.Success(episodeList))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            }
        }

    override fun getEpisodeById(id: String): Flow<Resource<Episode>> = flow {
        try {
            val episode = service.getEpisodeById(id).body()!!
            emit(Resource.Success(episode))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(TAG, "getAllCharacters: ${e.localizedMessage}")
        }
    }


}