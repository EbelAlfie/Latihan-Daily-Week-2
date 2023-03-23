package com.example.day_13.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.day_13.R
import com.example.day_13.Utils
import com.example.day_13.ViewModelFactory
import com.example.day_13.databinding.ActivityMainBinding
import com.example.day_13.model.AlamatModel
import com.example.day_13.viewmodel.MainViewModel

class MainActivity: AppCompatActivity(), AlamatAdapter.SetOnItemClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var alamatAdapter: AlamatAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var preference: SharedPreferences
    companion object {
        fun getIntent(context: Context) = Intent(context, TambahAlamatActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()

        preference = getSharedPreferences("Pref", Context.MODE_PRIVATE)

        if (preference.getBoolean("tertambah", false)) initData()

        initRv()
        observer()
        initOnClick()
    }

    @SuppressLint("CommitPrefEdits")
    private fun initData() {
        val data1 = AlamatModel(
                detailAlamat = "okokokokok",
                label = "Bali",
                namaPenerima = "Aku",
                nomorHandphone = "1231241",
                checked = true
            )
        val data2 = AlamatModel(
                detailAlamat = "euwhdhuewhufihiwufhiuehiwufhiwufhiwhuhuuhuhuhuhuhuhnjnjnjnjnjnjnkjnjnkjnjnjknkjnkjnkjnkkjnkjnjnjknjknjnkjnjknknjknkjnkjnkjnkjnkjnkjn",
                label = "Bandung",
                namaPenerima = "Aku",
                nomorHandphone = "1231241",
                checked = true
            )
        val data3 = AlamatModel(
                detailAlamat = "wkwkwkwkwk",
                label = "Manado",
                namaPenerima = "Aku",
                nomorHandphone = "1231241",
                checked = true
            )
        val data4 = AlamatModel(
                detailAlamat = "jakarta",
                label = "Jakarta",
                namaPenerima = "Aku",
                nomorHandphone = "1231241",
                checked = true
        )
        val data5 = AlamatModel(
                detailAlamat = "di hutan",
                label = "Papua",
                namaPenerima = "Aku",
                nomorHandphone = "1231241",
                checked = true
        )
        mainViewModel.setAlamat(data1)
        mainViewModel.setAlamat(data2)
        mainViewModel.setAlamat(data3)
        mainViewModel.setAlamat(data4)
        mainViewModel.setAlamat(data5)

        preference.edit().putBoolean("tertambah", true)
    }

    private fun observer() {
        mainViewModel.getAllAlamat().observe(this) {
            if (it == null) return@observe
            alamatAdapter.updateAlamat(it.toMutableList())
            //alamatAdapter.notifyItemChanged(0, alamatAdapter.itemCount)
        }
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory.getInstance(this.application)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun initRv() {
        binding.recListAlamat.layoutManager = LinearLayoutManager(this)
        alamatAdapter = AlamatAdapter(mutableListOf(), this)
        binding.recListAlamat.adapter = alamatAdapter
    }

    private fun initOnClick() {
        binding.tvTambahAlamat.setOnClickListener {
            //kirim -1 kalau add data
            val intent = getIntent(this)
            intent.putExtra(Utils.ADD_DATA_KEY, Utils.ADD_DATA_VALUE)
            startActivity(intent)
        }
    }

    override fun onDeleteItemListener(position: Int) {
        mainViewModel.deleteAlamat(alamatAdapter.getItemAt(position))
        //alamatAdapter.notifyItemRemoved(position)
        //alamatAdapter.notifyItemRangeChanged(position, alamatAdapter.itemCount)
        Toast.makeText(this, getString(R.string.data_deleted), Toast.LENGTH_SHORT).show()
    }

    override fun onUbahItemListener(position: Int) {
        val intent = getIntent(this)
        intent.putExtra(Utils.ADD_DATA_KEY, alamatAdapter.getItemAt(position).primaryKey)
        startActivity(intent)
    }
}