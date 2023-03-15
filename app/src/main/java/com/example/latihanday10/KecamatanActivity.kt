package com.example.latihanday10

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityKecamatanBinding
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.model.KecamatanModel
import com.example.latihanday10.retrofit.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KecamatanActivity : AppCompatActivity(), Adapter.ViewInteraction {
    private var data: GeneralModel? = null
    private lateinit var binding: ActivityKecamatanBinding
    private lateinit var daerahAdapter: Adapter
    private var daerahList = mutableListOf<GeneralModel>()
    private var provinsi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKecamatanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.intent.getParcelableExtra("kota", GeneralModel::class.java)
        else this.intent.getParcelableExtra("kota")

        provinsi = this.intent.getStringExtra("provinsi")

        if (data != null) {
            binding.tvProvinsi.text = provinsi
            binding.tvKota.text = data!!.nama
            getRespon(data!!.id)
        }
    }

    private fun getRespon(id: Int) {
        val response = RetrofitObj.apiService.getServiceKecamatan(id)
        response.enqueue(object: Callback<KecamatanModel> {
            override fun onResponse(call: Call<KecamatanModel>, response: Response<KecamatanModel>) {
                Log.d("DEBUG : ", response.body().toString())
                daerahList = response.body()!!.list
                initRecView()
            }

            override fun onFailure(call: Call<KecamatanModel>, t: Throwable) {
                Log.d("tests", t.message.toString())
            }

        })
    }
    private fun initRecView() {
        binding.rvListKecamatan.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListKecamatan.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val dataCamat = daerahList[position]
        val intent = Intent(this, KelurahanActivity::class.java)
        intent.putExtra("provinsi", provinsi)
        intent.putExtra("kota", data!!.nama)
        intent.putExtra("camat", dataCamat)
        startActivity(intent)
    }
}