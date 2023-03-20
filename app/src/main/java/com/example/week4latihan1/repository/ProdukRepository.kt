package com.example.week4latihan1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week4latihan1.model.ProdukModel
import com.example.week4latihan1.model.service.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukRepository {
    private var listProduk = MutableLiveData<MutableList<ProdukModel>>()

    fun getResponse(): LiveData<MutableList<ProdukModel>> {
        val respon = RetrofitObj.apiService.getAllProduk()
        respon.enqueue(object : Callback<MutableList<ProdukModel>> {
            override fun onResponse(call: Call<MutableList<ProdukModel>>, response: Response<MutableList<ProdukModel>>) {
                Log.d("tests", response.body().toString())
                listProduk.postValue(if (response.isSuccessful) response.body()
                else mutableListOf())
            }

            override fun onFailure(call: Call<MutableList<ProdukModel>>, t: Throwable) {
                Log.d("Error", t.message.toString())
                listProduk.postValue(mutableListOf())
            }
        })
        return listProduk
    }
}