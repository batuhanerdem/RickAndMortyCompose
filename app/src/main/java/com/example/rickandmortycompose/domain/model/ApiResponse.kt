package com.example.rickandmortycompose.domain.model

data class ApiResponse<T>(
    val result: T,
    val success: Boolean,
)
