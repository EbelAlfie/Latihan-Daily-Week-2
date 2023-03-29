package com.example.cleanarchmovieapp.data

import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.data.service.ApiService
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.example.cleanarchmovieapp.domain.MovieRepository
import com.example.cleanarchmovieapp.domain.QueryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImplement @Inject constructor(private val retrofitObj: ApiService): MovieRepository {
    override fun getPopularMovie(page: Int): Flow<QueryEntity> {
        return flow {
            try{
                val response = retrofitObj.getAllPopularMovies(Utils.API_KEY, page)
                emit(QueryEntity(MovieModel.convertList(response.result), "", false))
            } catch (e: Exception) {
                emit(QueryEntity(listOf(), e.message.toString(), false))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getOneMovie(id: Int): Flow<MovieEntity> {
        return flow {
            try{
                val response = retrofitObj.getSpecificMovie(id, Utils.API_KEY)
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