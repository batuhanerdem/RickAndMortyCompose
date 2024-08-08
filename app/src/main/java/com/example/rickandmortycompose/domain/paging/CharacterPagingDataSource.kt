package com.example.rickandmortycompose.domain.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortycompose.data.service.CharacterService
import com.example.rickandmortycompose.domain.model.Character
import com.example.rickandmortycompose.utils.ERROR

class CharacterPagingDataSource(private val characterService: CharacterService) :
    PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: STARTING_PAGE_INDEX
        Log.d("value", "load: test")
        Log.d("value", "load: $params ")
        return try {
            val response = characterService.getAllCharacters(page)
            Log.d("source", "load: ${response.body()}")
            LoadResult.Page(
                data = response.body()!!.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(1),
                nextKey = if (response.body()!!.results.isEmpty()) null else page.plus(1)
            )
        } catch (exception: Exception) {
            Log.d(ERROR, "load: $exception ")
            return LoadResult.Error(exception)
        }

    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}