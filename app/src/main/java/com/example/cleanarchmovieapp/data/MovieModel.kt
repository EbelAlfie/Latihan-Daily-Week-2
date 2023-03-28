package com.example.cleanarchmovieapp.data

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("backdrop_path")
    val image: String,
    @SerializedName("vote_average")
    val rating: Float,
    @SerializedName("release_date")
    val year: String,
    @SerializedName("original_title")
    val name: String,
    @SerializedName("overview")
    val desc: String,
)