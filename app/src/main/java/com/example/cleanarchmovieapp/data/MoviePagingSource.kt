package com.example.cleanarchmovieapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.data.di.LocalDbModule
import com.example.cleanarchmovieapp.data.service.ApiService
import com.example.cleanarchmovieapp.domain.MovieEntity

class MoviePagingSource (private val ApiObj: ApiService, private val dbObj: LocalDbModule, val mode: Int): PagingSource<Int, MovieEntity>() {
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val position = params.key ?: 1
        return try {
            val listData = if (mode == Utils.ONLINE) getOnlineList(position) else getOfflineList()

            LoadResult.Page(
                data = listData,
                nextKey = if (listData.isEmpty()) null else position + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getOfflineList(): List<MovieEntity> {
        return MovieModel.convertListToEntity(
            dbObj.MovieDao().getPrevList()
        )
    }

    private suspend fun getOnlineList(position: Int): List<MovieEntity> {
        val response = ApiObj.getAllPopularMovies(
            apiKey = Utils.API_KEY,
            page = if (position == 1) position else position * 10 - 10,
        )
        val data = MovieModel.convertList(response.result)

        data.forEach {
            dbObj.MovieDao().insertMovie(MovieModel.convertToMovieDbModel(it))
        }

        return data
    }
}