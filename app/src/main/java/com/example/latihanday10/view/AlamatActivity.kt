package com.example.latihanday10.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanday10.R
import com.example.latihanday10.databinding.ActivityAlamatBinding

class AlamatActivity: AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityAlamatBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var alamatViewModel: AlamatViewModel
    private lateinit var tvProvinsi: TextView
    private lateinit var tvKota: TextView
    private lateinit var tvKecamatan: TextView
    private lateinit var tvKelurahan: TextView
    private lateinit var rvListDaerah: RecyclerView
    private lateinit var btnGoBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        initViewInteraction()
        alamatViewModel.setNama(tvProvinsi.text.toString(), tvKota.text.toString(), tvKecamatan.text.toString(), tvKelurahan.text.toString())
        alamatViewModel.getAlamat()?.observe(this) {
            tvProvinsi.text = it.namaProvinsi
            tvKota.text = it.namaKota
            tvKecamatan.text = it.namaKecamatan
            tvKelurahan.text = it.namaKelurahan
        }
    }

    private fun begin() {
        alamatViewModel.setId(0, 0)
        alamatViewModel.getSavedData().observe(this) {
            daerahAdapter.updateData(it.list)
            setLoading(it.loading)
        }
        alamatViewModel.getAlamat()?.observe(this) {
            if (it.namaProvinsi != null && it.namaKecamatan != null && it.namaKota != null && it.namaKelurahan != null){
                setGoBack()
            }
        }
    }

    private fun initViewInteraction() {
        btnGoBack.setOnClickListener {
            hideBtn()
            begin()
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }

    private fun initView() {
        alamatViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[AlamatViewModel::class.java]
        tvProvinsi = binding.tvProvinsi
        tvKota = binding.tvKota
        tvKecamatan = binding.tvKecamatan
        tvKelurahan = binding.tvKelurahan
        rvListDaerah = binding.rvList
        btnGoBack = binding.btnBack

        rvListDaerah.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(mutableListOf(), this)
        rvListDaerah.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val namaDaerah = daerahAdapter.getString(position)
        val idDaerah = daerahAdapter.getId(position)
        when {
            tvProvinsi.visibility == View.GONE -> {setView(namaDaerah, 1, idDaerah, tvProvinsi)}
            tvKota.visibility == View.GONE -> {setView(namaDaerah,  2, idDaerah, tvKota)}
            tvKecamatan.visibility == View.GONE -> {setView(namaDaerah, 3, idDaerah, tvKecamatan)}
            tvKelurahan.visibility == View.GONE -> {setView(namaDaerah, 4, idDaerah, tvKelurahan)}
            else -> return
        }
    }

    private fun setView(namaDaerah: String, type: Int, requiredId: Int, textView: TextView) {
        textView.visibility = View.VISIBLE
        textView.text = namaDaerah
        if (type == 4) return
        alamatViewModel.setId(type, requiredId)
    }

    private fun setGoBack() {
        rvListDaerah.visibility = View.GONE
        btnGoBack.visibility = View.VISIBLE
        btnGoBack.text = getString(R.string.ganti)
    }

    private fun hideBtn() {
        btnGoBack.visibility = View.GONE
        binding.tvAlamat.visibility = View.GONE
        rvListDaerah.visibility = View.VISIBLE
        hideAllTv(tvProvinsi)
        hideAllTv(tvKota)
        hideAllTv(tvKecamatan)
        hideAllTv(tvKelurahan)
    }

    private fun hideAllTv(tv: TextView) {
        tv.visibility = View.GONE
    }
}