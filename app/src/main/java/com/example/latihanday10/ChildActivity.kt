package com.example.latihanday10

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.latihanday10.model.ProvinsiModel

class ChildActivity : AppCompatActivity() {
    private var data: ProvinsiModel? = null
    private var binding: ac
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)
        data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.intent.getParcelableExtra("data", ProvinsiModel::class.java)
        else this.intent.getParcelableExtra("data")

        if (data != null)
    }
}