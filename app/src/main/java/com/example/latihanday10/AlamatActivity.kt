package com.example.latihanday10

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.latihanday10.databinding.ActivityAlamatBinding
import com.example.latihanday10.model.GeneralModel

class AlamatActivity : AppCompatActivity() {
    private var data: GeneralModel? = null
    private lateinit var binding: ActivityAlamatBinding
    private var provinsi: String? = null
    private var kota: String? = null
    private var camat: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.intent.getParcelableExtra("lurah", GeneralModel::class.java)
        else this.intent.getParcelableExtra("lurah")

        provinsi = this.intent.getStringExtra("provinsi")
        kota = this.intent.getStringExtra("kota")
        camat = this.intent.getStringExtra("camat")

        if (data != null) {
            binding.tvProvinsi.text = getString(R.string.provinsi_adalah, provinsi)
            binding.tvKota.text = getString(R.string.kota_adalah, kota)
            binding.tvKecamatan.text = getString(R.string.kecamatan_adalah, camat)
            binding.tvKelurahan.text = getString(R.string.kelurahan_adalah, data!!.nama)
        }
    }
}