package com.example.latihanday8

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.latihanday8.databinding.DetailAlamatBinding

class DetailAlamatActivity: AppCompatActivity() {
    private lateinit var binding: DetailAlamatBinding

    private var alamat: AlamatModel? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailAlamatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            alamat = this.intent.getParcelableExtra("DataAlamat", AlamatModel::class.java)
            position = this.intent.getIntExtra("position", 0)
        }else {
            alamat = this.intent.getParcelableExtra("DataAlamat")
            position = this.intent.getIntExtra("position", 0)
        }
        setView()
        initInteraction()
    }

    private fun setView() {
        if (alamat != null) {
            binding.etDetailAlamat.setText(alamat!!.detailAlamat)
            binding.etLabel.setText(alamat!!.label)
            binding.etNamaPenerima.setText(alamat!!.namaPenerima)
            binding.etNoTelp.setText(alamat!!.nomorHandphone)
            binding.switchAlamatUtama.isChecked = alamat!!.checked
        }
    }

    private fun initInteraction() {
        binding.btnSimpan.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.apply {
                setTitle(getString(R.string.submit_title))
                setIcon(ContextCompat.getDrawable(this@DetailAlamatActivity, R.drawable.baseline_warning_24))
                setMessage(getString(R.string.submit_confirm_msg))
                setPositiveButton(getString(R.string.ya)) {_ ,_ ->
                    submit()
                }
                setNegativeButton(getString(R.string.tidak)) {dialog, _->
                    dialog.dismiss()
                }
                show()
            }

        }
    }

    private fun submit() {
        if (!gatherData()) return
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("DataAlamat", alamat)
        intent.putExtra("position", position)
        setResult(100, intent)
        finish()
    }

    private fun strLength(namaData: String, data: String, editText: EditText): Boolean {
        val msg = "$namaData terlalu panjang!"
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
            val msg = "$namaData tidak boleh kosong!"
            printError(msg, editText)
            true
        }else false
    }

    private fun printError(s: String, editText: EditText) {
        editText.error = s
        editText.requestFocus()
    }

    private fun gatherData(): Boolean{
        val label = binding.etLabel.text.toString()
        val detailAlamat = binding.etDetailAlamat.text.toString()
        val penerima = binding.etNamaPenerima.text.toString()
        val telp = binding.etNoTelp.text.toString()
        //blank string
        if (isEmptyStr(getString(R.string.alamat), detailAlamat, binding.etDetailAlamat) ||
            isEmptyStr(getString(R.string.label), label, binding.etLabel) ||
            isEmptyStr(getString(R.string.penerima), penerima, binding.etNamaPenerima) ||
            isEmptyStr(getString(R.string.notelp), telp, binding.etNoTelp)) return false
        //invalid length string
        if (!strLength(getString(R.string.label), label, binding.etLabel) ||
            !strLength(getString(R.string.alamat), detailAlamat, binding.etDetailAlamat) ||
            !strLength(getString(R.string.penerima), penerima, binding.etNamaPenerima) ||
            !strLength(getString(R.string.notelp), telp, binding.etNoTelp)) return false
        alamat = AlamatModel(detailAlamat, label, penerima, telp, binding.switchAlamatUtama.isChecked)
        return true
    }
}