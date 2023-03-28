package com.example.cleanarchmovieapp.domain

data class NetStatus(
    val errorMsg: String = "",
    val loadingStatus: Boolean = false
)