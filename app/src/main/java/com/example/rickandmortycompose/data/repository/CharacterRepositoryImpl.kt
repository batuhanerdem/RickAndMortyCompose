package com.example.rickandmortycompose.data.repository

import android.util.Log
import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.domain.model.AllCharactersDTO
import com.example.rickandmortycompose.domain.model.ApiResponse
import com.example.rickandmortycompose.domain.model.Result
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.utils.Resource
import com.example.rickandmortycompose.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject
import kotlin.Result as Result1

class CharacterRepositoryImpl @Inject constructor(private val service: CharacterService) :
    CharacterRepository {
    override suspend fun getAllCharacters(): Flow<Resource<List<Result>>> = flow {
        emit(Resource.Loading())
        try {
            val list = responseToDTO(service.getAllCharacters())
            if (list.isNotEmpty()) emit(Resource.Success(list))
//            else emit(Resource.Error("No Data Found"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
//            Log.d(TAG, "getAllCharacters: ${e.localizedMessage}")
        }

    }

    private fun responseToDTO(response: Response<ApiResponse<AllCharactersDTO>>): List<Result> {
        Log.d(TAG, "responseToDTO: ${response}, ${response.body()}")
        response.body()?.let {
            if (it.result == null) Log.d(TAG, "responseToDTO: nullmus")
            return it.result.results
        }
        return emptyList()
    }
}