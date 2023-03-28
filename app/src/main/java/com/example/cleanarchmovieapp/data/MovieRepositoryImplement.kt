package com.example.cleanarchmovieapp.data

import com.example.cleanarchmovieapp.domain.MovieUseCase
import com.example.cleanarchmovieapp.data.service.RetrofitObj
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.example.cleanarchmovieapp.domain.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MovieRepositoryImplement(): MovieRepository {
    override fun getPopularMovie(page: Int): Flow<QueryModel> {
        return flow {
            try{
                val response = RetrofitObj.retrofitInstance.getAllPopularMovies(RetrofitObj.API_KEY, page)
                emit(response)
            } catch (e: Exception) {
                emit(QueryModel(0, mutableListOf(), e.message.toString(), false))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getOneMovie(id: Int): Flow<MovieEntity> {
        return flow {
            try{
                val response = RetrofitObj.retrofitInstance.getSpecificMovie(id, RetrofitObj.API_KEY)
                emit(MovieModel.convert(response))
            } catch (e: Exception) {
                emit(MovieEntity(0,"", 0.0f,"","",""))
            }
        }.flowOn(Dispatchers.IO)
    }

//    override fun getOneMovie(): MovieModel {
//        return MovieModel()
//    }

}