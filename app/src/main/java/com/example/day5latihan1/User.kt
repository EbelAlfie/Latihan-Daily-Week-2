package com.example.day5latihan1

import android.os.Parcel
import android.os.Parcelable

data class User(
    var namaText: String  = "", var emailText: String = "",
    var passwordText: String = "", var alamatText: String = "", var gender: Boolean = false,
    var pendidikanTerakhir: String = "SD",
    var tanggalLahir: Int = 0, var bulanInt: Int = 0, var tahunlahir: Int = 0,
var index: Int = 0): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    fun getTglLahir(): String {
        return "$tanggalLahir/$bulanInt/$tahunlahir"
    }

    fun getGender(): String {
        return if(gender) "Perempuan" else "Laki"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaText)
        parcel.writeString(emailText)
        parcel.writeString(passwordText)
        parcel.writeString(alamatText)
        parcel.writeByte(if (gender) 1 else 0)
        parcel.writeString(pendidikanTerakhir)
        parcel.writeInt(tanggalLahir)
        parcel.writeInt(bulanInt)
        parcel.writeInt(tahunlahir)
        parcel.writeInt(index)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}



