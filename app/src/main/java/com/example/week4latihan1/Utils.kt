package com.example.week4latihan1

import android.content.Context
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.Html
import android.text.Spanned
import android.view.View
import android.widget.TextView
import com.example.week4latihan1.model.ProdukModel

object Utils {
    const val VISIBILITY_ON = true
    const val VISIBILITY_OFF = false
    const val DETIL_PRODUK_KEY = "produk"

    fun String.fromHtml(): Spanned {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    fun setDiskon(tvHargaNormal: TextView, tvDiskon: TextView, data: ProdukModel) {
        tvHargaNormal.text = ProdukModel.getHarga(data.hargaNormal)
        tvDiskon.text = ProdukModel.diskonToString(data.getDiskon())
    }

    fun setVisibility(tvDiskon: TextView, tvHargaNormal: TextView, isVisible: Boolean) {
        tvDiskon.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvHargaNormal.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvHargaNormal.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    fun isDiskon(tvDiskon: TextView, tvHargaNormal: TextView, diskon: Int): Boolean {
        setVisibility(tvDiskon, tvHargaNormal,
            when (diskon) {
                0 -> VISIBILITY_OFF
                else -> VISIBILITY_ON
            })
        return diskon != 0 //true = diskon
    }
}