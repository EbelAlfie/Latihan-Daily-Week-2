package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityKelurahanBinding
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.viewmodel.GlobalViewModel

class KelurahanActivity : AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityKelurahanBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var globalViewModel: GlobalViewModel
    private var dataProvinsi: GeneralModel? = null
    private var dataKota: GeneralModel? = null
    private var dataKecamatan: GeneralModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKelurahanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataProvinsi = Utils.getIntentData(this, Utils.PROVINSI_KEY)
        dataKota = Utils.getIntentData(this, Utils.KOTA_KEY)
        dataKecamatan = Utils.getIntentData(this, Utils.KECAMATAN_KEY)

        initView()
        dataProvinsi?.let{binding.tvProvinsi.text = it.nama}
        dataKota?.let{ binding.tvKota.text = it.nama }
        dataKecamatan?.let{
            binding.tvKecamatan.text = it.nama
            initObserver(it.id)
        }
    }

    private fun initObserver(id: Int) {
        globalViewModel.setList(3, id)
        globalViewModel.daerahListStatus().observe(this) {
            daerahAdapter.updateData(it.list)
            setProgressBar(it.loading)
        }
    }

    private fun setProgressBar(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun initView() {
        globalViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[GlobalViewModel::class.java]
        binding.rvListKelurahan.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(mutableListOf(), this)
        binding.rvListKelurahan.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, AlamatActivity::class.java)
        intent.putExtra(Utils.PROVINSI_KEY, dataProvinsi)
        intent.putExtra(Utils.KOTA_KEY, dataKota)
        intent.putExtra(Utils.KECAMATAN_KEY, dataKecamatan)
        intent.putExtra(Utils.KELURAHAN_KEY, daerahAdapter.getData(position))
        startActivity(intent)
    }
}