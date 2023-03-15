package com.example.latihanday10.model

import com.google.gson.annotations.SerializedName

data class GeneralModel (
    @SerializedName("id")
    var id : Int,
    @SerializedName("nama")
    var nama: String
)