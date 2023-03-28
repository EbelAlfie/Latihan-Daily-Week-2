package com.example.cleanarchmovieapp.domain

import com.google.gson.annotations.SerializedName

data class MovieEntity(
    val id: Int,
    val image: String,
    val rating: Float,
    val year: String,
    val name: String,
    val desc: String,
)