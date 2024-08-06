package com.example.rickandmortycompose.domain.use_case

import com.example.rickandmortycompose.domain.model.Episode
import com.example.rickandmortycompose.domain.repository.EpisodeRepository
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class GetMultipleEpisodesUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {
    fun execute(episodeIdPair: Pair<Int, Int>): Flow<Resource<List<Episode>>> {
        val episodeIdListString = episodeIdPair.generateEpisodeIdList().generateEpisodeListString()
        return episodeRepository.getMultipleEpisodes(episodeIdListString)
    }

    private fun List<Int>.generateEpisodeListString(): String {
        return this.joinToString(separator = ",")
    }

    private fun Pair<Int, Int>.generateEpisodeIdList(): List<Int> {
        val idList = mutableListOf<Int>()
        for (id in this.first..this.second) {
            idList.add(id)
        }
        return idList
    }
}