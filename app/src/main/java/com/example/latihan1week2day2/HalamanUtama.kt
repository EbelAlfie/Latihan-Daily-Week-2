package com.example.latihan1week2day2

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HalamanUtama: AppCompatActivity() {
    private lateinit var greetings: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.halaman_utama)
        val user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.intent.getParcelableExtra("Data User", User::class.java)
        } else {
            this.intent.getParcelableExtra("Data User")
        }
        greetings = findViewById(R.id.message)
        if (user != null) {
            greetings.text = getString(R.string.login_berhasil, user.name)
        }
    }
}