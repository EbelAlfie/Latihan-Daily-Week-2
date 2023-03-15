package com.example.latihanday10.retrofit

import com.example.latihanday10.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("provinsi")
    fun getServiceProvinsi(): Call<ProvinsiModel>//Call<GeneralModel> //
    @GET("kota")
    fun getServiceKota(@Query("id_provinsi") idProvinsi: Int): Call<ProvinsiModel>//Call<MutableList<KotaModel>>
    @GET("kecamatan")
    fun getServiceKecamatan(@Query("id_kota") idProvinsi: Int): Call<ProvinsiModel>//Call<MutableList<KotaModel>>
    @GET("kelurahan")
    fun getServiceKelurahan(@Query("id_kecamatan") idProvinsi: Int): Call<ProvinsiModel>//Call<MutableList<KotaModel>>
}