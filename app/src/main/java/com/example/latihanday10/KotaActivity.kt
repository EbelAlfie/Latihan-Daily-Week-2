package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.Utils.KOTA_KEY
import com.example.latihanday10.Utils.PROVINSI_KEY
import com.example.latihanday10.Utils.getIntentData
import com.example.latihanday10.databinding.ActivityKotaBinding
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.viewmodel.GlobalViewModel

class KotaActivity : AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityKotaBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var globalViewModel: GlobalViewModel
    private var dataProvinsi: GeneralModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataProvinsi = getIntentData(this, PROVINSI_KEY)
        initView()
        dataProvinsi?.let{
            binding.tvProvinsi.text = it.nama
            initObserver(it.id)
        }
    }

    private fun initObserver(id: Int) {
        globalViewModel.setList(1, id)
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
        binding.rvListKota.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(mutableListOf(), this)
        binding.rvListKota.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, KecamatanActivity::class.java)
        intent.putExtra(PROVINSI_KEY, dataProvinsi)
        intent.putExtra(KOTA_KEY, daerahAdapter.getData(position))
        startActivity(intent)
    }
}