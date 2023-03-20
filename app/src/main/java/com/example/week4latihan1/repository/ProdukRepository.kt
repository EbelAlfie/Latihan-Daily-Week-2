package com.example.week4latihan1.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week4latihan1.model.AllDataModel
import com.example.week4latihan1.model.ProdukModel
import com.example.week4latihan1.model.service.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProdukRepository {
    private var listProduk = MutableLiveData<AllDataModel>()

    fun getResponse(): LiveData<AllDataModel> {
        val respon = RetrofitObj.apiService.getAllProduk()
        respon.enqueue(object : Callback<MutableList<ProdukModel>> {
            override fun onResponse(call: Call<MutableList<ProdukModel>>, response: Response<MutableList<ProdukModel>>) {
                Log.d("tests", response.body().toString())
                listProduk.postValue(
                    if (response.isSuccessful && !response.body().isNullOrEmpty()){
                        AllDataModel(
                            produk = if (response.isSuccessful) response.body()!!
                            else mutableListOf(),
                            error = ""
                        )
                    } else {
                          AllDataModel(
                              produk = mutableListOf(),
                              error = response.errorBody()!!.string()
                          )
                    }
                )
            }

            override fun onFailure(call: Call<MutableList<ProdukModel>>, t: Throwable) {
                Log.d("Error", t.message.toString())
                listProduk.value = AllDataModel(
                    produk = mutableListOf(),
                    error = t.message.toString()
                )
            }
        })
        return listProduk
    }
}