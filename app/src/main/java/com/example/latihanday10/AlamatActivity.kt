package com.example.latihanday10

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityAlamatBinding
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.retrofit.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlamatActivity: AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityAlamatBinding
    private var daerahList = mutableListOf<GeneralModel>() //MutableList<GeneralModel>? = null //
    private lateinit var daerahAdapter: Adapter
    private lateinit var tvProvinsi: TextView
    private lateinit var tvKota: TextView
    private lateinit var tvKecamatan: TextView
    private lateinit var tvKelurahan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        getRespon(0, 0, initRecView)
    }

    private fun setProgressBar(visibility: Boolean) {
        binding.progressBar.isVisible =  visibility
    }

    private fun initView() {
        tvProvinsi = binding.tvProvinsi
        tvKota = binding.tvKota
        tvKecamatan = binding.tvKecamatan
        tvKelurahan = binding.tvKelurahan

    }

    private fun getRespon(type: Int, requiredId: Int, func: () -> Unit) {
        if (type == 4) return
        val response = getResponseType(type, requiredId)
        setProgressBar(Utils.PROGRESS_BAR_VISIBLE)
        response?.enqueue(object: Callback<GatewayModel> {
            override fun onResponse(call: Call<GatewayModel>, response: Response<GatewayModel>) {
                Log.d("DEBUG : ", response.body().toString())
                if (daerahList.isNotEmpty()) daerahList.clear()
                daerahList.addAll(response.body()!!.list)
                func()
                setProgressBar(Utils.PROGRESS_BAR_GONE)
            }

            override fun onFailure(call: Call<GatewayModel>, t: Throwable) {
                Log.d("tests", t.message.toString())
                setProgressBar(Utils.PROGRESS_BAR_GONE)
            }
        })
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

    private val initRecView: () -> Unit = {
        binding.rvListProvinsi.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListProvinsi.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val data = daerahList[position]
        when {
            tvProvinsi.visibility == View.GONE -> {setView(data, 1, tvProvinsi)}
            tvKota.visibility == View.GONE -> {setView(data, 2, tvKota)}
            tvKecamatan.visibility == View.GONE -> {setView(data, 3, tvKecamatan)}
            tvKelurahan.visibility == View.GONE -> {setView(data, 4, tvKelurahan)}
            else -> {}
        }
    }

    private val refreshList: () -> Unit = {
        Log.d("initag",  daerahList.toString())
        daerahAdapter.notifyItemRangeChanged(0, daerahList.size)
    }

    private fun setView(data: GeneralModel, type: Int, tv: TextView) {
        tv.visibility = View.VISIBLE
        tv.text = data.nama
        getRespon(type, data.id, refreshList)
    }
}