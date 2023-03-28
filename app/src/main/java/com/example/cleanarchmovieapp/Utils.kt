package com.example.cleanarchmovieapp

import android.content.Context
import android.content.Intent

object Utils {
    const val ID_KEY = "id"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"

    fun initIntent(context: Context, kelas: Class<*>, item: Int){
        val intent = Intent(context, kelas)
        intent.putExtra(ID_KEY, item)
        context.startActivity(intent)
    }
}