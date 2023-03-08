package com.example.day5latihan1

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PageAwal: AppCompatActivity() {
    private lateinit var tvMsg : TextView
    private lateinit var buttonRegis: Button
    private var dataUser: User? = null

    private val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == 100 && result.data != null) {
            dataUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data!!.getParcelableExtra("Data User", User::class.java)
            } else {
                result.data!!.getParcelableExtra("Data User")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.page_awal)
        initView()

        dataUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
             this.intent!!.getParcelableExtra("Data User", User::class.java)
        } else {
            this.intent!!.getParcelableExtra("Data User")
        }
        //launchIntent()

        tvMsg.text = if (dataUser == null) getString(R.string.user_notfound) else getString(R.string.info_user, dataUser!!.namaText, dataUser!!.emailText, dataUser!!.getGender(), dataUser!!.getTglLahir(), dataUser!!.pendidikanTerakhir, dataUser!!.alamatText)
        buttonRegis.text = if (dataUser == null) getString(R.string.registrasi) else getString(R.string.ubah_data)
        onClickListener()
    }

    private fun launchIntent(intent: Intent) {
        resultLauncher.launch(intent)
    }

    private fun onClickListener() {
        buttonRegis.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Data user", dataUser)
            launchIntent(intent)
            finish()
        }
    }

    private fun initView() {
        tvMsg = findViewById(R.id.tv_msg)
        buttonRegis = findViewById(R.id.btn_regis)
    }
}