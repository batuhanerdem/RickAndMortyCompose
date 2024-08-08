package com.example.rickandmortycompose.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.domain.paging.CharacterPagingDataSource
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.utils.ERROR
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val service: CharacterService) :
    CharacterRepository {
    override fun getAllCharacters(): Flow<PagingData<Character>> {
        val value2 = Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE
        ), pagingSourceFactory = { CharacterPagingDataSource(service) }).flow
        Log.d("tag", "getAllCharacters:  calisti")
        return value2
    }

    override suspend fun getAllCharacters2() {

        return Pager(config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE
        ), pagingSourceFactory = { CharacterPagingDataSource(service) }).flow.collect { char ->
            char.map { Log.d("page", "getAllCharacters2: it") }

        }
    }

    override fun getCharacterById(id: String): Flow<Resource<Character>> = flow {
        try {
            val character = service.getCharacterById(id).body()!!
            emit(Resource.Success(character))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(ERROR, "getCharacterById: ${e.localizedMessage}")
        }
    }

    override fun getMultipleCharacters(ids: String): Flow<Resource<List<Character>>> = flow {
        try {
            val characterList = service.getMultipleCharacters(ids).body()!!
            emit(Resource.Success(characterList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(ERROR, "getMultipleCharacters: message: ${e.localizedMessage}")
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}