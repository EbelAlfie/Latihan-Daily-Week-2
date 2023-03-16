package com.example.latihanday10

import android.app.Activity
import android.os.Build
import com.example.latihanday10.model.GeneralModel

object Utils {
    const val PROVINSI_KEY = "provinsi"
    const val KOTA_KEY = "kota"
    const val KECAMATAN_KEY = "kecamatan"
    const val KELURAHAN_KEY = "kelurahan"

    fun getIntentData(activity: Activity, key: String): GeneralModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) activity.intent.getParcelableExtra(key, GeneralModel::class.java)
        else activity.intent.getParcelableExtra(key)
    }
}