package com.example.latihanday10.model

import com.google.gson.annotations.SerializedName

data class KotaModel (
    @SerializedName("kota_kabupaten")
    var list: MutableList<GeneralModel>
)