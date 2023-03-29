package com.example.cleanarchmovieapp.presentation.di

import com.example.cleanarchmovieapp.domain.MovieUseCase
import com.example.cleanarchmovieapp.domain.MovieUseCaseReal
import dagger.Binds
import dagger.Module

@Module
abstract class MainModule {
    @Binds
    abstract fun provideMovieUseCase(movieUseCase: MovieUseCaseReal): MovieUseCase
}