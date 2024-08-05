package com.example.rickandmortycompose.utils

import kotlinx.coroutines.flow.MutableStateFlow

fun MutableStateFlow<String>.clear() {
    this.value = ""
}