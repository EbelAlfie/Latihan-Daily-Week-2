package com.example.cleanarchmovieapp.domain

import androidx.paging.PagingData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseReal @Inject constructor(private val repo: MovieRepository): MovieUseCase {
    override fun getPopularMovie(scope: CoroutineScope, mode: Int): Flow<PagingData<MovieEntity>> {
        return repo.getPopularMovie(scope, mode)
    }

    override fun getOneMovie(id: Int): Flow<MovieEntity> {
        return repo.getOneMovie(id)
    }

    override fun insertMovie(movie: MovieEntity) {
        repo.insert(movie)
    }

}