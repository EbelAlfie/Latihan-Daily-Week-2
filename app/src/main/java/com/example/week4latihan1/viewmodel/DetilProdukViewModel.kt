package com.example.week4latihan1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.week4latihan1.model.ProdukModel

class DetilProdukViewModel: ViewModel() {
    private val dataProduk= MutableLiveData<ProdukModel>()
    fun getProduk(): LiveData<ProdukModel> = dataProduk

    fun setProduk(data: ProdukModel?) {
        data?.let {
            dataProduk.value = it
        }
    }
}