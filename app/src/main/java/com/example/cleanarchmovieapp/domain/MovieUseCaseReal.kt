package com.example.cleanarchmovieapp.domain

import com.example.cleanarchmovieapp.data.MovieRepositoryImplement
import com.example.cleanarchmovieapp.data.QueryModel
import kotlinx.coroutines.flow.Flow

class MovieUseCaseReal(private val repo: MovieUseCase): MovieUseCase {
    override fun getPopularMovie(page: Int): Flow<QueryModel> {
        return repo.getPopularMovie(page)
    }

}