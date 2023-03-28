package com.example.cleanarchmovieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.data.QueryModel
import com.example.cleanarchmovieapp.domain.MovieUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: MovieUseCase): ViewModel() {
    private var _popularMovieData = MutableLiveData<QueryModel>()
    fun getPopularMovieData(): LiveData<QueryModel> = _popularMovieData

    private var _specificMovieData = MutableLiveData<MovieModel>()
    fun getSpecificMovie(): LiveData<MovieModel> = _specificMovieData

    init {
        getPopularMovie(20)
    }

    fun getPopularMovie(page: Int) {
        _popularMovieData.value = QueryModel(0, mutableListOf(), "", true)
        viewModelScope.launch {
            useCase.getPopularMovie(page).collect {
                _popularMovieData.postValue(it)
            }
        }
    }

    fun setMovie(id: Int) {
        viewModelScope.launch {
            useCase.getOneMovie(id).collect {
                _specificMovieData.postValue(it)
            }
        }
    }
}