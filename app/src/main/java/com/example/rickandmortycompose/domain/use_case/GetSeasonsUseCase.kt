package com.example.rickandmortycompose.domain.use_case

import android.util.Log
import com.example.rickandmortycompose.domain.model.Episode
import com.example.rickandmortycompose.domain.model.Season
import com.example.rickandmortycompose.domain.repository.EpisodeRepository
import com.example.rickandmortycompose.utils.IntUtils
import com.example.rickandmortycompose.utils.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
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
            delay(10000) //testing
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
            Log.d("tag", "execute: ${combinedList.count()}")
            combinedList.map { generateSeasonList(it) }
            Log.d(" tag", "execute: $seasonList")
        }
    }


    private fun generateSeasonList(episode: Episode) {
        Log.d("all", "generateSeasonList: all episodes: ${episode.id}")
        val episodeRangePair = episode.episode.getSeasonAndEpisode() ?: return
        val isSeasonAdded = containsSeason(episodeRangePair.first)
        if (isSeasonAdded) {
            val season = seasonList.find { it.number == episodeRangePair.first } ?: return
            val lastEpisode = IntUtils.getGreater(season.episodeRange.second, episode.id)
            val firstEpisode = IntUtils.getLower(season.episodeRange.first, episode.id)
            season.episodeRange = Pair(firstEpisode, lastEpisode)
        } else {
            Log.d("episode", "\n\n$episode")
            val seasonNumber = episode.episode.getSeasonAndEpisode()?.first ?: return
            val seasonRangePair = Pair(episode.id, episode.id)
            seasonList.add(
                Season(
                    seasonNumber, seasonRangePair, episode.airDate
                )
            )
        }
    }

    private fun containsSeason(seasonNumber: Int): Boolean {
        seasonList.map {
            if (it.number == seasonNumber) return true
        }
        return false
    }

    private fun String.getSeasonAndEpisode(): Pair<Int, Int>? {
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