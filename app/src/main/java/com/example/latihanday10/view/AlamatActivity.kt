package com.example.latihanday10.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityAlamatBinding
import com.example.latihanday10.model.GatewayModel

class AlamatActivity: AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityAlamatBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var alamatViewModel: AlamatViewModel
    private lateinit var tvProvinsi: TextView
    private lateinit var tvKota: TextView
    private lateinit var tvKecamatan: TextView
    private lateinit var tvKelurahan: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        alamatViewModel.getSavedData().observe(this) {
            setViewVal(it)
            setLoading(it.loading)
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun setViewVal(it: GatewayModel) {
        binding.rvListProvinsi.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(it.list, this)
        binding.rvListProvinsi.adapter = daerahAdapter
    }

    private fun initView() {
        alamatViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[AlamatViewModel::class.java]
        tvProvinsi = binding.tvProvinsi
        tvKota = binding.tvKota
        tvKecamatan = binding.tvKecamatan
        tvKelurahan = binding.tvKelurahan
    }

    override fun onClick(position: Int) {
        TODO("Not yet implemented")
    }
}