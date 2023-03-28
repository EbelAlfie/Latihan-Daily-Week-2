package com.example.cleanarchmovieapp.data.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "78735294e6c5f10cefe449732a2bfbd8"
    val retrofitInstance: ApiService by lazy {
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        }.build().create(ApiService::class.java)
    }
}