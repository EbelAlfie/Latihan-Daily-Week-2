package com.example.cleanarchmovieapp.data.service

import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.data.QueryModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getAllPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): QueryModel

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ) : Flow<QueryModel>

    @GET("Movies")
    suspend fun getSpecificMovie(@Query("") id: Int, @Query("api_key") key: String): Flow<MovieModel>

    @GET("movie/latest")
    suspend fun getLatestMovie(@Query("api_key") key: String): Flow<QueryModel>
}