package com.example.cleanarchmovieapp.data.di

import com.example.cleanarchmovieapp.data.MovieRepositoryImplement
import com.example.cleanarchmovieapp.domain.MovieRepository
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class, LocalDbModule::class])
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repo: MovieRepositoryImplement): MovieRepository
}