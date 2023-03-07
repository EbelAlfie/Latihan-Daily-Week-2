package com.example.latihan1week2day2

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val name: String, val password: String): Parcelable