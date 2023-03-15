package com.example.latihanday10

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityKelurahanBinding
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.model.KelurahanModel
import com.example.latihanday10.retrofit.RetrofitObj
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KelurahanActivity : AppCompatActivity(), Adapter.ViewInteraction {
    private var data: GeneralModel? = null
    private lateinit var binding: ActivityKelurahanBinding
    private lateinit var daerahAdapter: Adapter
    private var daerahList = mutableListOf<GeneralModel>()
    private var provinsi: String? = null
    private var kota: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.intent.getParcelableExtra("camat", GeneralModel::class.java)
        else this.intent.getParcelableExtra("camat")

        provinsi = this.intent.getStringExtra("provinsi")
        kota = this.intent.getStringExtra("kota")

        if (data != null) {
            binding.tvProvinsi.text = getString(R.string.provinsi_adalah, provinsi)
            binding.tvKota.text = getString(R.string.kota_adalah, kota)
            binding.tvKecamatan.text = getString(R.string.kecamatan_adalah, data!!.nama)
            getRespon(data!!.id)
        }
    }

    private fun getRespon(id: Int) {
        val response = RetrofitObj.apiService.getServiceKelurahan(id)
        response.enqueue(object: Callback<KelurahanModel> {
            override fun onResponse(call: Call<KelurahanModel>, response: Response<KelurahanModel>) {
                Log.d("DEBUG : ", response.body().toString())
                daerahList = response.body()!!.list
                initRecView()
            }

            override fun onFailure(call: Call<KelurahanModel>, t: Throwable) {
                Log.d("tests", t.message.toString())
            }

        })
    }
    private fun initRecView() {
        binding.rvListKelurahan.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListKelurahan.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val dataLurah = daerahList[position]
        val intent = Intent(this, AlamatActivity::class.java)
        intent.putExtra("provinsi", provinsi)
        intent.putExtra("kota", kota)
        intent.putExtra("camat", data!!.nama)
        intent.putExtra("lurah", dataLurah)
        startActivity(intent)
    }
}