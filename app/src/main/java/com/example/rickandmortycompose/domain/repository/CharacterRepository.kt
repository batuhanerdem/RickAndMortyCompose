package com.example.rickandmortycompose.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharacters(): Flow<PagingData<Character>>
    suspend fun getAllCharacters2()

    fun getCharacterById(id: String): Flow<Resource<Character>>
    fun getMultipleCharacters(ids: String): Flow<Resource<List<Character>>>
}