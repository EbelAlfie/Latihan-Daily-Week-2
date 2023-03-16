package com.example.latihanday10.model

import com.google.gson.annotations.SerializedName

data class GatewayModel(
    @SerializedName("provinsi", alternate = ["kota_kabupaten", "kecamatan", "kelurahan"])
    var list: MutableList<GeneralModel> = mutableListOf(),
    var loading: Boolean = false
    )