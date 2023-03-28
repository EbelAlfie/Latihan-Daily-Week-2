package com.example.cleanarchmovieapp.domain

import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovie(page: Int): Flow<QueryEntity>
    fun getOneMovie(id: Int): Flow<MovieEntity>
}