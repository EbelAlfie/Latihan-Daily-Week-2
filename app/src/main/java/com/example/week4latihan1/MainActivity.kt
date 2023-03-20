package com.example.week4latihan1

import android.content.Intent
import android.os.Bundle
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
            setError(getString(R.string.no_internet))
            return
        }
        initView()
        setObserver()
    }

    private fun setObserver() {
        mainViewModel.getProdukList().observe(this) {
            if (it.error.isNotBlank()) setError(it.error)
            if (it == null) return@observe
            produkAdapter.updateProduk(it.produk)
        }
    }

    private fun setError(errorMsg: String) {
        val dialog = Utils.setUpDialog(errorMsg, this)
        dialog.setPositiveButton(getString(R.string.ok)) {diag,_ -> diag.dismiss() }
        dialog.show()
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