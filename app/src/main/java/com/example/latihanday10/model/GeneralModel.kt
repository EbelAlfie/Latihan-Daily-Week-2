package com.example.latihanday10.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneralModel (
    @SerializedName("id")
    var id : Int,
    @SerializedName("nama")
    var nama: String
): Parcelable