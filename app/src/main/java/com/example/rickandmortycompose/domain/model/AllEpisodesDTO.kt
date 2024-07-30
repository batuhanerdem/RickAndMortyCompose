package com.example.rickandmortycompose.domain.model

data class AllEpisodesDTO(
    val info: Info,
    val results: List<Episode>
)