package com.example.latihanday10.model.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {

    const val urlBase = "https://dev.farizdotid.com/api/daerahindonesia/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder().apply {
            baseUrl(urlBase)
            //client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().logging.setLevel(Level.BODY)).build())
            addConverterFactory(GsonConverterFactory.create())
        }
    }

    val apiService: ApiService by lazy {
        retrofitBuilder.build().create(ApiService::class.java)
    }
}