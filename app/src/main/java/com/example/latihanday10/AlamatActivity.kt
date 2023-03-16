package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.latihanday10.databinding.ActivityAlamatBinding
import com.example.latihanday10.model.GeneralModel

class AlamatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlamatBinding
    private var dataProvinsi: GeneralModel? = null
    private var dataKota: GeneralModel? = null
    private var dataKecamatan: GeneralModel? = null
    private var dataKelurahan: GeneralModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataProvinsi = Utils.getIntentData(this, Utils.PROVINSI_KEY)
        dataKota = Utils.getIntentData(this, Utils.KOTA_KEY)
        dataKecamatan = Utils.getIntentData(this, Utils.KECAMATAN_KEY)
        dataKelurahan = Utils.getIntentData(this, Utils.KELURAHAN_KEY)

        if (dataProvinsi == null && dataKota == null &&
            dataKecamatan == null && dataKelurahan == null) {
            hideView(binding.tvProvinsi)
            hideView(binding.tvKota)
            hideView(binding.tvKecamatan)
            hideView(binding.tvKelurahan)
        }else {
            showView(binding.tvProvinsi)
            showView(binding.tvKota)
            showView(binding.tvKecamatan)
            showView(binding.tvKelurahan)

            binding.btnGantiAlamat.text = getString(R.string.ganti_alamat)

            dataProvinsi?.let{binding.tvProvinsi.text = it.nama}
            dataKota?.let{binding.tvKota.text = it.nama }
            dataKecamatan?.let{binding.tvKecamatan.text = it.nama }
            dataKelurahan?.let{binding.tvKelurahan.text = it.nama}
        }

        binding.btnGantiAlamat.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun showView(tv: TextView) {
        tv.visibility = View.VISIBLE
    }

    private fun hideView(tv: TextView) {
        tv.visibility = View.GONE
    }
}