package com.example.rickandmortycompose.domain.repository

import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacters(): Flow<Resource<List<Character>>>
    fun getCharacterById(id: String): Flow<Resource<Character>>
}