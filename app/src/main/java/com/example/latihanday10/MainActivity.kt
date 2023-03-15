package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
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
    private lateinit var tvProvinsi: TextView
    private lateinit var tvKota: TextView
    private lateinit var tvKecamatan: TextView
    private lateinit var tvKelurahan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        getRespon(0, 0, initRecView)

    }

    private fun initView() {
        tvProvinsi = binding.tvProvinsi
        tvKota = binding.tvKota
        tvKecamatan = binding.tvKecamatan
        tvKelurahan = binding.tvKelurahan
    }

    private fun getRespon(type: Int, requiredId: Int, func: () -> Unit) {
        val response = getResponseType(type, requiredId)
        response.enqueue(object: Callback<ProvinsiModel> {
            override fun onResponse(call: Call<ProvinsiModel>, response: Response<ProvinsiModel>) {
                Log.d("DEBUG : ", response.body().toString())
                daerahList = response.body()!!.list
                func()
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

    val initRecView: () -> Unit = {
        binding.rvListProvinsi.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListProvinsi.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val data = daerahList[position]
        when {
            tvProvinsi.visibility == View.GONE -> {setView(data, 0, tvProvinsi)}
            tvKota.visibility == View.GONE -> {setView(data, 1, tvKota)}
            tvKecamatan.visibility == View.GONE -> {setView(data, 2, tvKecamatan)}
            tvKelurahan.visibility == View.GONE -> {setView(data, 3, tvKelurahan)}
        }
    }

    val refreshList: () -> Unit = {
        daerahAdapter.notifyItemChanged(daerahList.size)
    }

    private fun setView(data: GeneralModel, type: Int, tv: TextView) {
        tv.visibility = View.VISIBLE
        tv.text = data.nama
        getRespon(type, data.id, refreshList)
    }
}