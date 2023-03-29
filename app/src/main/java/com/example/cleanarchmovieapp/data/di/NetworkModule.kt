package com.example.cleanarchmovieapp.data.di

import com.example.cleanarchmovieapp.data.service.ApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {
    private val BASE_URL = "https://api.themoviedb.org/3/"

    @Provides
    fun getRetrofit(): ApiService {
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }.build().create(ApiService::class.java)
    }
}