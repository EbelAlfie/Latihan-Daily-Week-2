package com.example.cleanarchmovieapp

import android.app.Application
import com.example.cleanarchmovieapp.data.di.DaggerDataComponents
import com.example.cleanarchmovieapp.data.di.DataComponents
import com.example.cleanarchmovieapp.presentation.di.ComponentPenghubung
import com.example.cleanarchmovieapp.presentation.di.DaggerComponentPenghubung

class MovieApp: Application() {
    private val dataComponents: DataComponents by lazy {
        DaggerDataComponents.factory().create(applicationContext)
    }

    val componentPenghubung: ComponentPenghubung by lazy {
        DaggerComponentPenghubung.factory().create(dataComponents)
    }
}