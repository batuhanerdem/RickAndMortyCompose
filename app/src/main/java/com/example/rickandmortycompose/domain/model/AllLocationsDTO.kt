package com.example.rickandmortycompose.domain.model

data class AllLocationsDTO(
    val info: Info,
    val results: List<Location>
)