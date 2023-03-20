package com.example.week4latihan1

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4latihan1.Utils.fromHtml
import com.example.week4latihan1.Utils.isDiskon
import com.example.week4latihan1.Utils.setDiskon
import com.example.week4latihan1.databinding.DetailSatuProdukBinding
import com.example.week4latihan1.model.ProdukModel
import com.example.week4latihan1.viewmodel.DetilProdukViewModel

class DetilProdukActivity: AppCompatActivity() {
    private lateinit var binding: DetailSatuProdukBinding
    private lateinit var detailProdukViewModel: DetilProdukViewModel
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailSatuProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setObserver()
    }

    private fun setObserver() {
        getDataFromPrevActivity()
        detailProdukViewModel.getProduk().observe(this) {
            if (it == null) {
                getDataFromPrevActivity()
            }else {
                bindData(it)
            }
        }
    }

    private fun bindData(it: ProdukModel) {
        val diskonStatus = isDiskon(binding.tvDiskonDisplay, binding.tvHargaProdukNormalDisplay, it.getDiskon())
        binding.tvTitleProdukDisplay.text = it.namaProduk
        binding.tvStokDariDisplay.text = if (it.stokProduk != 0) it.printStok() else getString(R.string.stok_habis)
        binding.tvDeskripsiProdukDisplay.text = it.deskripsiProduk.fromHtml()
        binding.tvHargaProdukSpecialDisplay.text = ProdukModel.getHarga(
            if (diskonStatus) it.hargaSpesial!! else it.hargaNormal
        )

        imageAdapter.insertItem(it.gambarProduk[0].imageUrl)

        if (diskonStatus) setDiskon(binding.tvHargaProdukNormalDisplay, binding.tvDiskonDisplay, it)
    }

    private fun getDataFromPrevActivity() {
        detailProdukViewModel.setProduk(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                this.intent.getParcelableExtra(Utils.DETIL_PRODUK_KEY, ProdukModel::class.java)
            else this.intent.getParcelableExtra(Utils.DETIL_PRODUK_KEY)
        )
    }

    private fun initView() {
        detailProdukViewModel = ViewModelProvider(this)[DetilProdukViewModel::class.java]
        binding.rvGambarProdukDisplay.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageAdapter = ImageAdapter(mutableListOf())
        binding.rvGambarProdukDisplay.adapter = imageAdapter
    }
}