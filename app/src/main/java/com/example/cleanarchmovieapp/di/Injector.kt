package com.example.cleanarchmovieapp.di

import com.example.cleanarchmovieapp.data.MovieRepositoryImplement
import com.example.cleanarchmovieapp.domain.MovieUseCase
import com.example.cleanarchmovieapp.domain.MovieUseCaseReal

object Injector {
    fun useCaseProvider(): MovieUseCase {
        val repo = repositoryProvider()
        return MovieUseCaseReal(repo)
    }
    fun repositoryProvider(): MovieUseCase {
        return MovieRepositoryImplement()
    }
}