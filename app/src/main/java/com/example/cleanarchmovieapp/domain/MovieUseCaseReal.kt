package com.example.cleanarchmovieapp.domain

import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.data.QueryModel
import kotlinx.coroutines.flow.Flow

class MovieUseCaseReal(private val repo: MovieRepository): MovieUseCase {
    override fun getPopularMovie(page: Int): Flow<QueryModel> {
        return repo.getPopularMovie(page)
    }

    override fun getOneMovie(id: Int): Flow<MovieEntity> {
        return repo.getOneMovie(id)
    }

}