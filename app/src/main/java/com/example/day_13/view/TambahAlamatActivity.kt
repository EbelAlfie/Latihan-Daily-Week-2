package com.example.day_13.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.day_13.Utils
import com.example.day_13.Utils.FEMALE
import com.example.day_13.Utils.MALE
import com.example.day_13.ViewModelFactory
import com.example.day_13.databinding.DetailAlamatBinding
import com.example.day_13.model.AlamatModel
import com.example.day_13.viewmodel.MainViewModel

class TambahAlamatActivity: AppCompatActivity() {
    private lateinit var binding: DetailAlamatBinding
    private lateinit var addAlamatViewModel: MainViewModel
    private var modeOrPrimaryKey: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentFromPrev()

        initViewModel()
        //kalau ada intent,
        if (modeOrPrimaryKey != Utils.ADD_DATA_VALUE) setObserver(modeOrPrimaryKey)

        iniSetOnClick()
    }

    private fun getIntentFromPrev() {
        modeOrPrimaryKey = this.intent.getIntExtra(Utils.ADD_DATA_KEY, Utils.ADD_DATA_VALUE)
    }

    private fun setObserver(id : Int) {
        addAlamatViewModel.getSpesificAlamat(id).observe(this) {
            if (it == null) return@observe
            setView(it)
        }
    }

    private fun setView(it: AlamatModel) {
        binding.etDetailAlamat.setText(it.detailAlamat)
        binding.etLabel.setText(it.label)
        binding.etNamaPenerima.setText(it.namaPenerima)
        binding.etNoTelp.setText(it.nomorHandphone)
        binding.switchAlamatUtama.isChecked = it.checked
    }

    private fun iniSetOnClick() {
        binding.btnSimpan.setOnClickListener {
            getInput()
        }
    }

    private fun getInput() {
        val detailAlamat = binding.etDetailAlamat.text.toString()
        val label = binding.etLabel.text.toString()
        val namaPenerima = binding.etNamaPenerima.text.toString()
        val nomorHandphone = binding.etNoTelp.text.toString()
        val checked = if (binding.switchAlamatUtama.isChecked) FEMALE else MALE

        val userInput = AlamatModel(
            detailAlamat = detailAlamat,
            label = label,
            namaPenerima = namaPenerima,
            nomorHandphone = nomorHandphone,
            checked = checked
        )
        if (modeOrPrimaryKey == -1) addAlamatViewModel.setAlamat(userInput)
        else {
            userInput.primaryKey = modeOrPrimaryKey
            addAlamatViewModel.updateAlamat(userInput)
        }
        finish()
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory.getInstance(this.application)
        addAlamatViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }
}