package com.example.cleanarchmovieapp.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieUseCaseReal @Inject constructor(private val repo: MovieRepository): MovieUseCase {
    override fun getPopularMovie(page: Int): Flow<QueryEntity> {
        return repo.getPopularMovie(page)
    }

    override fun getOneMovie(id: Int): Flow<MovieEntity> {
        return repo.getOneMovie(id)
    }

}