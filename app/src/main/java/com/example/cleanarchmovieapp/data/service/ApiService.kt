package com.example.cleanarchmovieapp.data.service

import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.data.QueryModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
    ): QueryModel

    @GET("movie/{id}")
    suspend fun getSpecificMovie(@Path("id") id: Int, @Query("api_key") key: String): MovieModel
}