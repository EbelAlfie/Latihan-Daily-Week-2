package com.example.cleanarchmovieapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleanarchmovieapp.data.QueryModel
import com.example.cleanarchmovieapp.domain.MovieUseCase
import kotlinx.coroutines.launch

class MainViewModel(private val useCase: MovieUseCase): ViewModel() {
    private var _movieData = MutableLiveData<QueryModel>()
    fun getMovieData(): LiveData<QueryModel> = _movieData

    init {
        getPopularMovie(20)
    }

    fun getPopularMovie(page: Int) {
        _movieData.value = QueryModel(0, mutableListOf(), "", true)
        viewModelScope.launch {
            useCase.getPopularMovie(page).collect {
                _movieData.postValue(it)
            }
        }
    }
}