package com.example.cleanarchmovieapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchmovieapp.domain.MovieUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private var movieUseCase: MovieUseCase
) : ViewModelProvider.NewInstanceFactory() {

    /*companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injector.useCaseProvider())
            }
    }*/

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel(movieUseCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
