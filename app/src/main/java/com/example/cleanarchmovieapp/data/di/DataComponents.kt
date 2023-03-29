package com.example.cleanarchmovieapp.data.di

import android.content.Context
import com.example.cleanarchmovieapp.domain.MovieRepository
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface DataComponents {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponents
    }

    fun provideRepository(): MovieRepository
}