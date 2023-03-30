package com.example.cleanarchmovieapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.data.service.ApiService
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.google.gson.Gson

class MoviePagingSource (private val ApiObj: ApiService): PagingSource<Int, MovieEntity>() {
    override fun getRefreshKey(state: PagingState<Int, MovieEntity>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        val position = params.key ?: 1
        return try {
            val response = ApiObj.getAllPopularMovies(
                apiKey = Utils.API_KEY,
                page = if (position == 1) position else position * 10 - 10,
            )
            val listData =  MovieModel.convertList(response.result)
            LoadResult.Page(
                data = listData,
                nextKey = if (listData.isEmpty()) null else position + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}