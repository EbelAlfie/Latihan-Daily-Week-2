package com.example.cleanarchmovieapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getPopularMovie(scope: CoroutineScope, mode: Int): Flow<PagingData<MovieEntity>>
    fun getOneMovie(id: Int): Flow<MovieEntity>

    fun deleteAll()
}