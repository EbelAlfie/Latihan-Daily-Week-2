package com.example.cleanarchmovieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.example.cleanarchmovieapp.domain.MovieUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val useCase: MovieUseCase): ViewModel() {

    private var _specificMovieData = MutableLiveData<MovieEntity>()
    fun getSpecificMovie(): LiveData<MovieEntity> = _specificMovieData

    fun getPopularMovie(mode: Int): Flow<PagingData<MovieEntity>> {
        return useCase.getPopularMovie(viewModelScope, mode)
    }

    fun setMovie(id: Int) {
        viewModelScope.launch {
            useCase.getOneMovie(id).collect {
                _specificMovieData.postValue(it)
            }
        }
    }

    fun deleteAll() {
        useCase.deleteAll()
    }

}