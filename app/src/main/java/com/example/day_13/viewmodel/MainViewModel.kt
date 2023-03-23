package com.example.day_13.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.day_13.model.AlamatModel
import com.example.day_13.repository.Repository

class MainViewModel(context: Context): ViewModel() {
    private val repository = Repository(context)
    //private var _dataAlamat = MutableLiveData<List<AlamatModel>>()

    //private var alamat = MutableLiveData<AlamatModel>()
    fun getSpesificAlamat(id: Int): LiveData<AlamatModel> = repository.getSpesificAlamat(id)

    fun getAllAlamat(): LiveData<List<AlamatModel>> = repository.getAllNotes()

    fun setAlamat(alamat: AlamatModel) {
        repository.insert(alamat)
    }

    fun deleteAlamat(alamat: AlamatModel) {
        repository.deleteAlamat(alamat)
    }

    fun updateAlamat(alamat: AlamatModel) {
        repository.updateAlamat(alamat)
    }
}