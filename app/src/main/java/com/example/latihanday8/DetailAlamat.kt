package com.example.latihanday8

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class DetailAlamat: AppCompatActivity() {
    private lateinit var etDetailAlamat: TextInputEditText
    private lateinit var etLabel: TextInputEditText
    private lateinit var etPenerima: TextInputEditText
    private lateinit var etNoTelp: TextInputEditText
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchAlamatUtama: Switch
    private lateinit var simpanBtn: Button
    private lateinit var tvHelperAlamat: TextView
    private lateinit var tvHelperLabel: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private var alamat: Alamat? = null
    private var position: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_alamat)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            alamat = this.intent.getParcelableExtra("DataAlamat", Alamat::class.java)
            position = this.intent.getIntExtra("position", 0)
        }else {
            alamat = this.intent.getParcelableExtra("DataAlamat")
            position = this.intent.getIntExtra("position", 0)
        }

        initView()
        if (alamat != null) setView()
        initInteraction()
    }

    private fun setView() {
        etDetailAlamat.setText(alamat!!.detailAlamat)
        etLabel.setText(alamat!!.label)
        etPenerima.setText(alamat!!.namaPenerima)
        etNoTelp.setText(alamat!!.nomorHandphone)
        switchAlamatUtama.isChecked = alamat!!.checked
    }

    private fun initInteraction() {
        simpanBtn.setOnClickListener {
            if (!gatherData()) return@setOnClickListener
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("DataAlamat", alamat)
            intent.putExtra("position", position)
            setResult(100, intent)
            finish()
        }
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
        val label = etLabel.text.toString()
        val detailAlamat = etDetailAlamat.text.toString()
        val penerima = etPenerima.text.toString()
        val telp = etNoTelp.text.toString()
        //blank string
        if (isEmptyStr(getString(R.string.alamat), detailAlamat, etDetailAlamat) ||
            isEmptyStr(getString(R.string.label), label, etLabel) ||
            isEmptyStr(getString(R.string.penerima), penerima, etPenerima) ||
            isEmptyStr(getString(R.string.notelp), telp, etNoTelp)) return false
        //invalid length string
        if (!strLength(getString(R.string.label), label, etLabel) ||
            !strLength(getString(R.string.alamat), detailAlamat, etDetailAlamat) ||
            !strLength(getString(R.string.penerima), penerima, etPenerima) ||
            !strLength(getString(R.string.notelp), telp, etNoTelp)) return false
        alamat = Alamat(detailAlamat, label, penerima, telp, switchAlamatUtama.isChecked)
        return true
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        etDetailAlamat = findViewById(R.id.et_detail_alamat)
        etLabel = findViewById(R.id.et_label)
        etPenerima = findViewById(R.id.et_namapenerima)
        etNoTelp = findViewById(R.id.et_notelp)
        switchAlamatUtama = findViewById(R.id.switch_alamatutama)
        tvHelperAlamat = findViewById(R.id.tv_detailalamat_helperlistener)
        tvHelperLabel = findViewById(R.id.tv_label_helperlistener)
        simpanBtn = findViewById(R.id.btn_simpan)
    }
}