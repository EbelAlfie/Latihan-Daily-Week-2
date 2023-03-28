package com.example.cleanarchmovieapp.domain

import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.data.QueryModel
import kotlinx.coroutines.flow.Flow

class MovieUseCaseReal(private val repo: MovieUseCase): MovieUseCase {
    override fun getPopularMovie(page: Int): Flow<QueryModel> {
        return repo.getPopularMovie(page)
    }

    override fun getOneMovie(id: Int): Flow<MovieModel> {
        return repo.getOneMovie(id)
    }

}