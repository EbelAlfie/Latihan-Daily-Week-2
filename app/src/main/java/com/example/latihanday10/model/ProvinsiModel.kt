package com.example.latihanday10.model

import com.google.gson.annotations.SerializedName

data class ProvinsiModel(
    @SerializedName("provinsi")
    var list: MutableList<GeneralModel>
    )