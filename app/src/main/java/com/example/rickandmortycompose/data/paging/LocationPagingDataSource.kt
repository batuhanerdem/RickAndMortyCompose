package com.example.rickandmortycompose.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortycompose.data.service.LocationService
import com.example.rickandmortycompose.domain.model.Location
import com.example.rickandmortycompose.utils.ERROR

class LocationPagingDataSource(private val characterService: LocationService) :
    PagingSource<Int, Location>() {
    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = characterService.getAllLocations(page)
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