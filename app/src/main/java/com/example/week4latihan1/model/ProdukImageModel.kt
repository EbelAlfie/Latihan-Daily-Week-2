package com.example.week4latihan1.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProdukImageModel(
    @SerializedName("url")
    var imageUrl: MutableList<String>
): Parcelable
