package com.example.latihanday10.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.latihanday10.Utils
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.model.retrofit.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DaerahRepository {
    private val daerahList = MutableLiveData<GatewayModel>()

    fun getRespon(type: Int, requiredId: Int): LiveData<GatewayModel> {
        daerahList.value = GatewayModel(
            list = mutableListOf(),
            loading = true
        )
        val response = getResponseType(type, requiredId)
        response?.enqueue(object: Callback<GatewayModel> {
            override fun onResponse(call: Call<GatewayModel>, response: Response<GatewayModel>) {
                Log.d("DEBUG : ", response.body().toString())
                daerahList.value = if (response.isSuccessful) {
                    GatewayModel(
                        list = response.body()!!.list,
                        loading = false
                    )
                } else {
                    GatewayModel(
                        list = mutableListOf(),
                        loading = false
                    )
                }
            }

            override fun onFailure(call: Call<GatewayModel>, t: Throwable) {
                Log.d("tests", t.message.toString())
                GatewayModel(
                    list = mutableListOf(),
                    loading = false
                )
            }
        })
        return daerahList
    }

    private fun getResponseType(type: Int, param: Int): Call<GatewayModel>? {
        return when(type) {
            0 -> RetrofitObj.apiService.getServiceProvinsi()
            1 -> RetrofitObj.apiService.getServiceKota(param)
            2 -> RetrofitObj.apiService.getServiceKecamatan(param)
            3 -> RetrofitObj.apiService.getServiceKelurahan(param)
            else -> null
        }
    }
}