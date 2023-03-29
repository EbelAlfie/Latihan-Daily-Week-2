package com.example.cleanarchmovieapp.presentation.di

import androidx.core.view.KeyEventDispatcher
import com.example.cleanarchmovieapp.data.di.DataComponents
import com.example.cleanarchmovieapp.presentation.MainActivity
import com.example.cleanarchmovieapp.presentation.MovieDetailsActivity
import dagger.Component

@AppScope
@Component(
    modules=[MainModule::class],
    dependencies=[DataComponents::class])
interface ComponentPenghubung {
    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponents): ComponentPenghubung
    }

    fun injectMain(activity: MainActivity)
    fun injectDetail(activity: MovieDetailsActivity)
}