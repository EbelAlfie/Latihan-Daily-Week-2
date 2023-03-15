package com.example.latihanday10.model

import com.google.gson.annotations.SerializedName

data class KecamatanModel (
    @SerializedName("kecamatan")
    var list: MutableList<GeneralModel>
)