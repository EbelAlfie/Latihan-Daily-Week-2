package com.example.week4latihan1.model.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {
    private const val BASE_URL = "http://35.240.238.167:8081/"

    val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
        }
    }

    val apiService: ApiService by lazy {
        retrofit.build().create(ApiService::class.java)
    }
}