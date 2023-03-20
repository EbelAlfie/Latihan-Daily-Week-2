package com.example.week4latihan1

import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week4latihan1.Utils.fromHtml
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

        if (diskonStatus) setDiskon(binding, it)
    }

    private fun setDiskon(binding: DetailSatuProdukBinding, data: ProdukModel) {
        binding.tvHargaProdukNormalDisplay.text = ProdukModel.getHarga(data.hargaNormal)
        binding.tvDiskonDisplay.text = ProdukModel.diskonToString(data.getDiskon())
    }

    private fun setVisibility(tvDiskon: TextView, tvHargaNormal: TextView, isVisible: Boolean) {
        tvDiskon.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvHargaNormal.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvHargaNormal.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun isDiskon(tvDiskon: TextView, tvHargaNormal: TextView, diskon: Int): Boolean {
        setVisibility(tvDiskon, tvHargaNormal,
            when (diskon) {
                0 -> Utils.VISIBILITY_OFF
                else -> Utils.VISIBILITY_ON
            })
        return diskon != 0 //true = diskon
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