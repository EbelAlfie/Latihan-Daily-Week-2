package com.example.week4latihan1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.week4latihan1.model.ProdukModel
import com.example.week4latihan1.repository.ProdukRepository

class MainViewModel: ViewModel() {
    private var produkList = MutableLiveData<MutableList<ProdukModel>>()
    private val repository = ProdukRepository()
    init {
        getDataResponse()
    }

    fun getProdukList(): LiveData<MutableList<ProdukModel>> = produkList

    private fun getDataResponse(){
        produkList = repository.getResponse() as MutableLiveData<MutableList<ProdukModel>>
    }

//    fun getResponse() {
//        val respon = RetrofitObj.apiService.getAllProduk()
//        respon.enqueue(object : Callback<MutableList<ProdukModel>> {
//            override fun onResponse(call: Call<MutableList<ProdukModel>>, response: Response<MutableList<ProdukModel>>) {
//                Log.d("tests", response.body().toString())
//                produkList.value = if (response.isSuccessful) response.body()
//                else mutableListOf()
//            }
//
//            override fun onFailure(call: Call<MutableList<ProdukModel>>, t: Throwable) {
//                Log.d("Error", t.message.toString())
//                produkList.value = mutableListOf()
//            }
//        })
//    }
}