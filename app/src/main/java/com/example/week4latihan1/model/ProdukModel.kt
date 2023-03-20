package com.example.week4latihan1.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat

@Parcelize
data class ProdukModel(
    @SerializedName("product_id")
    var idProduk: Long,
    @SerializedName("product_name")
    var namaProduk: String,
    @SerializedName("product_desc")
    var deskripsiProduk: String,
    @SerializedName("product_sku")
    var sku: String,
    @SerializedName("product_stock")
    var stokProduk: Int,
    @SerializedName("product_alfagift_price")
    var hargaNormal: Long,
    @SerializedName("product_special_price")
    var hargaSpesial: Long?,
    @SerializedName("product_images")
    var gambarProduk: MutableList<ProdukImageModel>,
): Parcelable {
    companion object {
        fun diskonToString(diskon: Int): String {
            return "${diskon}%"
        }
        fun getHarga(harga: Long): String {
            return DecimalFormat("Rp #,###").format(harga)
        }
    }

    fun getDiskon(): Int {
        return when (hargaSpesial){
            null, hargaNormal -> 0
            else -> ((hargaNormal.toDouble() - hargaSpesial!!)*100/hargaNormal).toInt()
        }
    }

    fun printStok(): String {
        return if (stokProduk > 1) "$stokProduk pcs" else "$stokProduk pc"
    }
}
