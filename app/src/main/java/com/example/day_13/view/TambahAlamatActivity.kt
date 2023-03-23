package com.example.day_13.view

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.day_13.R
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

        if (isEmptyStr(getString(R.string.alamat), detailAlamat, binding.etDetailAlamat) ||
            isEmptyStr(getString(R.string.label), label, binding.etLabel) ||
            isEmptyStr(getString(R.string.penerima), namaPenerima, binding.etNamaPenerima) ||
            isEmptyStr(getString(R.string.notelp), nomorHandphone, binding.etNoTelp)) return
        //invalid length string
        if (!strLength(getString(R.string.label), label, binding.etLabel) ||
            !strLength(getString(R.string.alamat), detailAlamat, binding.etDetailAlamat) ||
            !strLength(getString(R.string.penerima), namaPenerima, binding.etNamaPenerima) ||
            !strLength(getString(R.string.notelp), nomorHandphone, binding.etNoTelp)) return

        val userInput = AlamatModel(
            detailAlamat = detailAlamat,
            label = label,
            namaPenerima = namaPenerima,
            nomorHandphone = nomorHandphone,
            checked = checked
        )
        if (modeOrPrimaryKey == -1) {
            addAlamatViewModel.setAlamat(userInput)
            showToast(getString(R.string.data_added))
        }
        else {
            userInput.primaryKey = modeOrPrimaryKey
            addAlamatViewModel.updateAlamat(userInput)
            showToast(getString(R.string.data_changed))
        }
        finish()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        val viewModelFactory = ViewModelFactory.getInstance(this.application)
        addAlamatViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private fun strLength(namaData: String, data: String, editText: EditText): Boolean {
        val msg = getString(R.string.invalid_panjang, namaData)
        return when(namaData) {
            getString(R.string.label) -> {
                if (data.length > 30) { printError(msg, editText); false } else true
            }
            getString(R.string.alamat) ->{
                if (data.length > 100) { printError(msg, editText); false} else true
            }
            getString(R.string.penerima) -> {
                if (data.length > 64) {printError(msg, editText); false} else true
            }
            getString(R.string.notelp) -> {
                if (data.length < 12) {printError(getString(R.string.short_telp), editText); false}
                else if (data.length > 12) {printError(msg, editText); false} else true
            }
            else -> true
        }
    }

    private fun isEmptyStr(namaData: String, data: String, editText: EditText): Boolean {
        return if (data.isBlank()) {
            val msg = getString(R.string.blank_str,namaData)
            printError(msg, editText)
            true
        }else false
    }

    private fun printError(s: String, editText: EditText) {
        editText.error = s
        editText.requestFocus()
    }
}