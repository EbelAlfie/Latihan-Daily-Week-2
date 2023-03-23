package com.example.day_13.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AlamatModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var primaryKey: Int = 0,
    @ColumnInfo(name = "detail alamat")
    var detailAlamat: String,
    @ColumnInfo(name = "Label")
    var label: String,
    @ColumnInfo(name = "Nama Penerima")
    var namaPenerima: String,
    @ColumnInfo(name = "Nomor Handphone")
    var nomorHandphone: String,
    @ColumnInfo(name = "Alamat utama")
    var checked: Boolean
    )
{
    fun getLabelTelp(): String {
        return "$namaPenerima - $nomorHandphone"
    }
}