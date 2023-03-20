package com.example.week4latihan1

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week4latihan1.databinding.ItemProdukBinding
import com.example.week4latihan1.model.ProdukModel
import com.squareup.picasso.Picasso

class ProdukAdapter(val produkList: MutableList<ProdukModel>, val listener: SetOnClick): RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder>() {
    class ProdukViewHolder(val binding: ItemProdukBinding): RecyclerView.ViewHolder(binding.root){
        val tvTitleProduk = binding.tvTitleProduk
        val tvHargaNormalProduk = binding.tvHargaNormal
        val tvHargaSpecialProduk = binding.tvHargaSpesial
        val tvdiskon = binding.tvDiskon
        val ivGambarProduk = binding.ivGambarProduk
        val tvStok = binding.tvKeranjang
    }

    interface SetOnClick {
        fun onClick(position: Int)
    }

    fun updateProduk(newList: MutableList<ProdukModel>) {
        if (produkList.isNotEmpty()) produkList.clear()
        produkList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdukViewHolder {
        return ProdukViewHolder(ItemProdukBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return produkList.size
    }

    override fun onBindViewHolder(holder: ProdukViewHolder, position: Int) {
        val data = produkList[position]
        val diskonStatus = isDiskon(holder.tvdiskon, holder.tvHargaNormalProduk, data.getDiskon())
        holder.tvTitleProduk.text = if (data.namaProduk.length > 25) "${data.namaProduk.substring(0,24)}..." else data.namaProduk

        holder.tvHargaSpecialProduk.text = ProdukModel.getHarga(
            if (diskonStatus) data.hargaSpesial!! else data.hargaNormal
        )

        if (diskonStatus) setDiskon(holder, data)

        holder.tvStok.text = if (data.stokProduk != 0) data.printStok() else "Stok Habis"

        Picasso.get()
            .load(data.gambarProduk[0].imageUrl[0])
            .resize(300, 300)
            .into(holder.ivGambarProduk)

        holder.binding.root.setOnClickListener {
            listener.onClick(position)
        }
    }

    private fun setDiskon(holder: ProdukViewHolder, data: ProdukModel) {
        holder.tvHargaNormalProduk.text = ProdukModel.getHarga(data.hargaNormal)
        holder.tvdiskon.text = ProdukModel.diskonToString(data.getDiskon())
    }

    private fun setVisibility(tvDiskon: TextView, tvHargaNormal: TextView, isVisible: Boolean) {
        tvDiskon.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvHargaNormal.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        tvHargaNormal.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun isDiskon(tvDiskon: TextView, tvHargaNormal: TextView, diskon: Int): Boolean {
        setVisibility(tvDiskon, tvHargaNormal,
            when (diskon) {
            0 -> Utils.VISIBILITY_OFF
            else -> Utils.VISIBILITY_ON
        })
        return diskon != 0 //true = diskon
    }

    fun getData(position: Int): ProdukModel {
        return produkList[position]
    }
}