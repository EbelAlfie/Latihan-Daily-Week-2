package com.example.week4latihan1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.week4latihan1.databinding.ActivityMainBinding
import com.example.week4latihan1.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), ProdukAdapter.SetOnClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var produkAdapter: ProdukAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!Utils.isNetworkAvailable(this)) {
            Toast.makeText(
                this,
                getString(R.string.no_internet),
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }
        initView()
        setObserver()
    }

    private fun setObserver() {
        mainViewModel.getProdukList().observe(this) {
            if (it == null) return@observe
            produkAdapter.updateProduk(it)
        }
    }

    private fun initView() {
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.rvListProduk.layoutManager = GridLayoutManager(this, 2)
        produkAdapter = ProdukAdapter(mutableListOf(), this)
        binding.rvListProduk.adapter = produkAdapter
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, DetilProdukActivity::class.java)
        intent.putExtra(Utils.DETIL_PRODUK_KEY, produkAdapter.getData(position))
        startActivity(intent)
    }
}