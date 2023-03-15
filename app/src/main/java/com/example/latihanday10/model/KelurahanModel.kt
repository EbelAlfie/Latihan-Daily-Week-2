package com.example.latihanday10.model

import com.google.gson.annotations.SerializedName

data class KelurahanModel (
    @SerializedName("kelurahan")
    var list: MutableList<GeneralModel>
)