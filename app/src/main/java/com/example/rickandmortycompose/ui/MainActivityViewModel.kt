package com.example.rickandmortycompose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import com.example.rickandmortycompose.utils.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(characterRepository: CharacterRepository) :
    ViewModel() {
    init {
        viewModelScope.launch {

            characterRepository.getAllCharacters().collect { result ->
//                Log.d(TAG, "$result :data ${result.data}: \n error ${result.message} ")
//                when(result){
//                    is Resource.Error -> TODO()
//                    is Resource.Loading -> TODO()
//                    is Resource.Success -> TODO()
//                }

            }
        }
    }
}