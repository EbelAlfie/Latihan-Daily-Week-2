package com.example.day5latihan1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PageAwal: AppCompatActivity() {
    private lateinit var tvMsg : TextView
    private lateinit var buttonRegis: Button
    private var dataUser: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_awal)
        initView()
        dataUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
             this.intent.getParcelableExtra("Data User", User::class.java)
        else this.intent.getParcelableExtra("Data User")
        tvMsg.text = if (dataUser == null) getString(R.string.user_notfound) else getString(R.string.info_user, dataUser!!.namaText, dataUser!!.emailText, dataUser!!.getGender(), dataUser!!.getTglLahir(), dataUser!!.pendidikanTerakhir, dataUser!!.alamatText)
        buttonRegis.text = if (dataUser == null) getString(R.string.registrasi) else getString(R.string.ubah_data)
        onClickListener()
    }

    private fun onClickListener() {
        buttonRegis.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Data user", dataUser)
            startActivity(intent)
        }
    }

    private fun initView() {
        tvMsg = findViewById(R.id.tv_msg)
        buttonRegis = findViewById(R.id.btn_regis)
    }
}