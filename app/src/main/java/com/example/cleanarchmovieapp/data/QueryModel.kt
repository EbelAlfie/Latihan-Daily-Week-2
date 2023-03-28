package com.example.cleanarchmovieapp.data

import com.google.gson.annotations.SerializedName

data class QueryModel (
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("results")
    var result: MutableList<MovieModel> = mutableListOf(),
    var errorMsg: String = "",
    var loadingStatus: Boolean = false
)