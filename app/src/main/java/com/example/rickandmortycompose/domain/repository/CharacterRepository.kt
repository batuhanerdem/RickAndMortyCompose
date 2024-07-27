package com.example.rickandmortycompose.domain.repository

import com.example.rickandmortycompose.domain.model.Result
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getAllCharacters(): Flow<Resource<List<Result>>>
}