package com.example.cleanarchmovieapp.domain

data class QueryEntity(
    var result: List<MovieEntity> = listOf(),
    var errorMsg: String = "",
    var loadingStatus: Boolean = false
)
