package com.example.cleanarchmovieapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.data.service.ApiService
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.example.cleanarchmovieapp.domain.MovieRepository
import com.example.cleanarchmovieapp.domain.QueryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImplement @Inject constructor(private val retrofitObj: ApiService): MovieRepository {
    override fun getPopularMovie(scope: CoroutineScope): Flow<PagingData<MovieEntity>> {
        return Pager(config = PagingConfig(Utils.DEFAULT_SIZE)) {
            MoviePagingSource(retrofitObj)
        }.flow.cachedIn(scope)
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