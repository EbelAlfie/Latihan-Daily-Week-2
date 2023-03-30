package com.example.cleanarchmovieapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseReal @Inject constructor(private val repo: MovieRepository): MovieUseCase {
    override fun getPopularMovie(scope: CoroutineScope): Flow<PagingData<MovieEntity>> {
        return repo.getPopularMovie(scope)
    }

    override fun getOneMovie(id: Int): Flow<MovieEntity> {
        return repo.getOneMovie(id)
    }

}