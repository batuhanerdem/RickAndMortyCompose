package com.example.rickandmortycompose.domain.use_case

import com.example.rickandmortycompose.domain.model.Episode
import com.example.rickandmortycompose.domain.model.Season
import com.example.rickandmortycompose.domain.repository.EpisodeRepository
import com.example.rickandmortycompose.utils.IntUtils
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class GetSeasonsUseCase @Inject constructor(private val episodeRepository: EpisodeRepository) {
    private val seasonList = mutableListOf<Season>()
    fun execute(): Flow<Resource<List<Season>>> = flow {
        val flowList = mutableListOf<Flow<Resource<List<Episode>>>>()

        episodeRepository.getPageCount().collect {
            it.data?.let {
                repeat(it) { page ->
                    flowList.add(episodeRepository.getAllEpisodes(page + 1))
                }
            }
        }
        combine(flowList) { flows ->
            val combinedList = mutableListOf<Episode>()
            flows.forEach { resource ->
                resource.data?.let { combinedList.addAll(it) }
            }
            combinedList
        }.collect { combinedList ->
            combinedList.map { generateSeasonList(it) }
            emit(Resource.Success(seasonList))
        }
    }


    private fun generateSeasonList(episode: Episode) {
        val seasonNumber = episode.episode.getSeasonAndEpisodeAsPair()?.first ?: return
        if (seasonNumber.containsSeason()) { // if the season is defined in seasonList
            // in case the episodes don't come in an order
            // check if the episode is the first or the last of the season
            val season = seasonList.find { it.number == seasonNumber } ?: return
            val lastEpisode = IntUtils.getGreater(season.episodeRange.second, episode.id)
            val firstEpisode = IntUtils.getLower(season.episodeRange.first, episode.id)
            season.episodeRange = Pair(firstEpisode, lastEpisode) // set the season episode range
        } else {    // initialize the season
            val seasonRangePair = Pair(episode.id, episode.id)
            seasonList.add(
                Season(
                    seasonNumber, seasonRangePair, episode.airDate
                )
            )
        }
    }

    private fun Int.containsSeason(): Boolean {
        seasonList.map {
            if (it.number == this) return true
        }
        return false
    }

    private fun String.getSeasonAndEpisodeAsPair(): Pair<Int, Int>? {
        val regex = """S(\d+)E(\d+)""".toRegex()
        val matchResult = regex.find(this)
        return if (matchResult != null) {
            val (season, episode) = matchResult.destructured
            Pair(season.toInt(), episode.toInt())
        } else {
            null
        }
    }

}