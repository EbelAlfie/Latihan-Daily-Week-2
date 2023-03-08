package com.example.day5latihan1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var namaText: String  = "",
    var emailText: String = "",
    var passwordText: String = "",
    var alamatText: String = "", var gender: Boolean = false,
    var pendidikanTerakhir: String = "SD",
    var tanggalLahir: Int = 0,
    var bulanInt: Int = 0,
    var tahunlahir: Int = 0): Parcelable {
    fun getTglLahir(): String {
        return "$tanggalLahir/$bulanInt/$tahunlahir"
    }

    fun getGender(): String {
        return if(gender) "Perempuan" else "Laki"
    }

}



