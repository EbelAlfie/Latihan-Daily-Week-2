package com.example.week4latihan1.model.service

import com.example.week4latihan1.model.ProdukModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    fun getAllProduk(): Call<MutableList<ProdukModel>>
}