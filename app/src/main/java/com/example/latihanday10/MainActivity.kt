package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityMainBinding
import com.example.latihanday10.viewmodel.GlobalViewModel

class MainActivity: AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityMainBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var globalViewModel: GlobalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        initObserver()
    }

    private fun initObserver() {
        globalViewModel.setList(0, 0)
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
        binding.rvListProvinsi.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(mutableListOf(), this)
        binding.rvListProvinsi.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val intent = Intent(this, KotaActivity::class.java)
        intent.putExtra(Utils.PROVINSI_KEY, daerahAdapter.getData(position))
        startActivity(intent)
    }
}