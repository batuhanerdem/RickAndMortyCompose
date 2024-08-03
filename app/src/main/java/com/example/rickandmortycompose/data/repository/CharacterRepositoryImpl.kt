package com.example.rickandmortycompose.data.repository

import android.util.Log
import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.utils.Resource
import com.example.rickandmortycompose.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val service: CharacterService) :
    CharacterRepository {
    override fun getAllCharacters(): Flow<Resource<List<Character>>> = flow {
        try {
            val list = service.getAllCharacters().body()!!.results
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(TAG, "getAllCharacters: ${e.localizedMessage}")
        }

    }

    override fun getCharacterById(id: String): Flow<Resource<Character>> = flow {
        try {
            val character = service.getCharacterById(id).body()!!
            emit(Resource.Success(character))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(TAG, "getCharacterById: ${e.localizedMessage}")
        }
    }

    override fun getMultipleCharacters(ids: String): Flow<Resource<List<Character>>> = flow {
        try {
            Log.d(TAG, "getMultipleCharacters: ids: $ids")
            Log.d(TAG, "getMultipleCharacters: body: ${service.getMultipleCharacters(ids)} $")
            val characterList = service.getMultipleCharacters(ids).body()!!
            emit(Resource.Success(characterList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
            Log.d(TAG, "getMultipleCharacters: message: ${e.localizedMessage}")
        }
    }

}