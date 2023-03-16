package com.example.latihanday10

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanday10.databinding.ActivityMainBinding
import com.example.latihanday10.model.GatewayModel
import com.example.latihanday10.model.GeneralModel
import com.example.latihanday10.model.retrofit.RetrofitObj
import com.example.latihanday10.viewmodel.GlobalViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity: AppCompatActivity(), Adapter.ViewInteraction {
    private lateinit var binding: ActivityMainBinding
    private lateinit var daerahAdapter: Adapter
    private lateinit var globalViewModel: GlobalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }


    private fun initView() {
        GlobalViewModel = ViewModel()
        binding.rvListProvinsi.layoutManager = LinearLayoutManager(this)
        daerahAdapter = Adapter(daerahList, this)
        binding.rvListProvinsi.adapter = daerahAdapter
    }

    override fun onClick(position: Int) {
        val data = daerahList[position]
        val intent = Intent(this, KotaActivity::class.java)
        intent.putExtra("provinsi", data)
        startActivity(intent)
    }
}