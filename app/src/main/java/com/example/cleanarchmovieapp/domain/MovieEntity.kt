package com.example.cleanarchmovieapp.domain

data class MovieEntity(
    val id: Int,
    val image: String,
    val rating: Float,
    val year: String,
    val name: String,
    val desc: String,
)