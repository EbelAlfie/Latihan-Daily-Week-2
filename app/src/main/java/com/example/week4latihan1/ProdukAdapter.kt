package com.example.week4latihan1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week4latihan1.Utils.isDiskon
import com.example.week4latihan1.Utils.setDiskon
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
        notifyItemRangeChanged(0, produkList.size)
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
        holder.tvTitleProduk.text = if (data.namaProduk.length > 20) "${data.namaProduk.substring(0,20)}..." else data.namaProduk

        holder.tvHargaSpecialProduk.text = ProdukModel.getHarga(
            if (diskonStatus) data.hargaSpesial!! else data.hargaNormal
        )

        if (diskonStatus) setDiskon(holder.tvHargaNormalProduk, holder.tvdiskon, data)

        holder.tvStok.text = if (data.stokProduk != 0) data.printStok() else "Stok Habis"

        Picasso.get()
            .load(data.gambarProduk[0].imageUrl[0])
            .resize(300, 300)
            .into(holder.ivGambarProduk)

        holder.binding.root.setOnClickListener {
            listener.onClick(position)
        }
    }

    fun getData(position: Int): ProdukModel {
        return produkList[position]
    }
}