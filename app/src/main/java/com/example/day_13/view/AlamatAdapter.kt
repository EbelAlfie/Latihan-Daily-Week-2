package com.example.day_13.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.day_13.databinding.AlamatItemBinding
import com.example.day_13.model.AlamatModel

class AlamatAdapter(private val alamatList: MutableList<AlamatModel>, private val listenerUtility: SetOnItemClicked): RecyclerView.Adapter<AlamatAdapter.AlamatViewHolder>() {
    class AlamatViewHolder(val binding: AlamatItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlamatViewHolder {
        return AlamatViewHolder(AlamatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return alamatList.size
    }

    override fun onBindViewHolder(holder: AlamatViewHolder, position: Int) {
        val data = alamatList[position]
        holder.binding.tvLabel.text = data.label
        holder.binding.tvLabelDanTelp.text = data.getLabelTelp()
        holder.binding.tvDetailAlamat.text = data.detailAlamat
        holder.binding.tvDetailKota.text = data.detailAlamat
        holder.binding.switchItemAlamat.isChecked = data.checked

        holder.binding.btnHapus.setOnClickListener {
            listenerUtility.onDeleteItemListener(position)
        }
        holder.binding.btnUbah.setOnClickListener {
            listenerUtility.onUbahItemListener(position)
        }
    }

    interface SetOnItemClicked {
        fun onDeleteItemListener(position: Int)
        fun onUbahItemListener(position: Int)
    }

    fun updateAlamat(alamat: List<AlamatModel>) {
        alamatList.clear()
        alamatList.addAll(alamat)
        notifyItemRangeChanged(0, alamat.size)
    }

    fun getItemAt(position: Int): AlamatModel {
        return alamatList[position]
    }

}