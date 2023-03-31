package com.example.cleanarchmovieapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object Utils {
    const val ID_KEY = "id"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    const val API_KEY = "78735294e6c5f10cefe449732a2bfbd8"
    const val DEFAULT_SIZE = 10
    const val ONLINE = 1

    fun initIntent(context: Context, kelas: Class<*>, item: Int){
        val intent = Intent(context, kelas)
        intent.putExtra(ID_KEY, item)
        context.startActivity(intent)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}