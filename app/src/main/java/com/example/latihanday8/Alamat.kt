package com.example.latihanday8

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alamat(var detailAlamat: String,
                  var label: String,
                  var namaPenerima: String,
                  var nomorHandphone: String,
                  var checked: Boolean): Parcelable
{
    fun getLabelTelp(): String {
        return "$namaPenerima - $nomorHandphone"
    }
}