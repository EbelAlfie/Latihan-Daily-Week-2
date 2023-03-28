package com.example.cleanarchmovieapp.domain

import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.data.QueryModel
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getPopularMovie(page: Int): Flow<QueryModel>
    fun getOneMovie(id: Int): Flow<MovieModel>
    //fun getNetStatus(): Flow<NetStatus>
}