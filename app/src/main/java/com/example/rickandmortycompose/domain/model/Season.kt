package com.example.rickandmortycompose.domain.model

data class Season(
    val number: Int, var episodeRange: Pair<Int, Int>, val airDate: String
) {
    val episodeCount get() = this.episodeRange.second - this.episodeRange.first + 1
}
