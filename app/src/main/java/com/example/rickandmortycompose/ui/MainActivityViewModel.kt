package com.example.rickandmortycompose.ui

import androidx.lifecycle.ViewModel
import com.example.rickandmortycompose.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

}

const val TAG = "tag"