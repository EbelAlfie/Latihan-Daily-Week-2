package com.example.latihanday10

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityKotaBinding
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.model.KotaModel
import com.example.latihanday10.model.ProvinsiModel
import com.example.latihanday10.retrofit.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KotaActivity : AppCompatActivity(), Adapter.ViewInteraction {
    private var data: GeneralModel? = null
    private lateinit var binding: ActivityKotaBinding
    private lateinit var daerahAdapter: Adapter
    private var daerahList = mutableListOf<GeneralModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.intent.getParcelableExtra("provinsi", GeneralModel::class.java)
        else this.intent.getParcelableExtra("provinsi")

        if (data != null) {
            binding.tvProvinsi.text = data!!.nama
            getRespon(data!!.id)
        }
    }

    private fun getRespon(id: Int) {
        val response = RetrofitObj.apiService.getServiceKota(id)
        response.enqueue(object: Callback<KotaModel> {
            override fun onResponse(call: Call<KotaModel>, response: Response<KotaModel>) {
                Log.d("DEBUG : ", response.body().toString())
                daerahList = response.body()!!.list
                initRecView()
            }

            override fun onFailure(call: Call<KotaModel>, t: Throwable) {
                Log.d("tests", t.message.toString())
            }

        })
    }
    private fun initRecView() {
        binding.rvListKota.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListKota.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val dataKota = daerahList[position]
        val intent = Intent(this, KecamatanActivity::class.java)
        intent.putExtra("provinsi", data!!.nama)
        intent.putExtra("kota", dataKota)
        startActivity(intent)
    }
}