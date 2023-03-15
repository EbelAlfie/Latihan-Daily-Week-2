package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityMainBinding
import com.example.latihanday10.model.ProvinsiModel
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.retrofit.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityMainBinding
    private var daerahList = mutableListOf<GeneralModel>() //MutableList<GeneralModel>? = null //
    private lateinit var daerahAdapter: Adapter
    private lateinit var frameProvinsi: FrameLayout
    private lateinit var frameKota: FrameLayout
    private lateinit var frameKecamatan: FrameLayout
    private lateinit var frameKelurahan: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        getRespon(0, 0)

    }

    private fun initView() {
        frameProvinsi = binding.fragmentProvinsi
        frameKota = binding.fragmentKota
        frameKelurahan = binding.fragmentKelurahan
        frameKecamatan = binding.fragmentKecamatan
    }

    private fun getRespon(type: Int, requiredId: Int) {
        val response = getResponseType(type, requiredId)
        response.enqueue(object: Callback<ProvinsiModel> {
            override fun onResponse(call: Call<ProvinsiModel>, response: Response<ProvinsiModel>) {
                Log.d("DEBUG : ", response.body().toString())
                daerahList = response.body()!!.list
                initRecView()
            }

            override fun onFailure(call: Call<ProvinsiModel>, t: Throwable) {
                Log.d("tests", t.message.toString())
            }

        })
    }

    private fun getResponseType(type: Int, param: Int): Call<ProvinsiModel> {
        return when(type) {
            0 -> RetrofitObj.apiService.getServiceProvinsi()
            1 -> RetrofitObj.apiService.getServiceKota(param)
            2 -> RetrofitObj.apiService.getServiceKecamatan(param)
            else -> RetrofitObj.apiService.getServiceKelurahan(param)
        }
    }

    private fun initRecView() {
        binding.rvListProvinsi.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListProvinsi.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val data = daerahList[position]
        val intent = Intent(this, ChildActivity::class.java)
        intent.putExtra("provinsi", data)
        intent.putExtra("mode", 0)
        startActivity(intent)
    }
}