package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.Utils.KECAMATAN_KEY
import com.example.latihanday10.Utils.KOTA_KEY
import com.example.latihanday10.Utils.PROVINSI_KEY
import com.example.latihanday10.databinding.ActivityKecamatanBinding
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.viewmodel.GlobalViewModel

class KecamatanActivity : AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityKecamatanBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var globalViewModel: GlobalViewModel
    private var dataProvinsi: GeneralModel? = null
    private var dataKota: GeneralModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKecamatanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataProvinsi = Utils.getIntentData(this, PROVINSI_KEY)
        dataKota = Utils.getIntentData(this, KOTA_KEY)

        initView()
        dataProvinsi?.let{binding.tvProvinsi.text = it.nama}
        dataKota?.let{
            binding.tvKota.text = it.nama
            initObserver(it.id)
        }
    }

    private fun initObserver(id: Int) {
        globalViewModel.setList(2, id)
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
        binding.rvListKecamatan.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(mutableListOf(), this)
        binding.rvListKecamatan.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, KelurahanActivity::class.java)
        intent.putExtra(PROVINSI_KEY, dataProvinsi)
        intent.putExtra(KOTA_KEY, dataKota)
        intent.putExtra(KECAMATAN_KEY, daerahAdapter.getData(position))
        startActivity(intent)
    }
}