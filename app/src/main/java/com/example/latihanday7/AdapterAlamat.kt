package com.example.latihanday7

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterAlamat(var listOfAlamat: MutableList<Alamat>):
    RecyclerView.Adapter<AdapterAlamat.AlamatViewHolder>() {
    var listenerUtility: Utility? = null

    interface Utility {
        fun onUbahItemListener(position: Int)
    }

    class AlamatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvLabel = itemView.findViewById<TextView>(R.id.label)
        val tvLabelTelp = itemView.findViewById<TextView>(R.id.label_dan_telp)
        val tvDetailAlamat = itemView.findViewById<TextView>(R.id.detail_alamat)
        val tvDetailKota = itemView.findViewById<TextView>(R.id.detail_kota)
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switchAlamat = itemView.findViewById<Switch>(R.id.switch_item_alamat)
        val btnHapus = itemView.findViewById<Button>(R.id.btn_hapus)
        val btnUbah = itemView.findViewById<Button>(R.id.btn_ubah)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlamatViewHolder {
        return AlamatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.alamat_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfAlamat.size
    }

    override fun onBindViewHolder(holder: AlamatViewHolder, position: Int) {
        val alamat = listOfAlamat[position]
        holder.tvLabel.text = alamat.label
        holder.tvLabelTelp.text = alamat.getLabelTelp()
        holder.tvDetailAlamat.text = alamat.detailAlamat
        holder.tvDetailKota.text = alamat.detailAlamat
        holder.switchAlamat.isChecked = alamat.checked

        holder.btnHapus.setOnClickListener {
            listOfAlamat.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, listOfAlamat.size)
        }

        holder.btnUbah.setOnClickListener {
            listenerUtility?.onUbahItemListener(position)
        }
    }
}