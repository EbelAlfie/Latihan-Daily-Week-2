package com.example.latihanday10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanday10.databinding.ItemProvinsiBinding
import com.example.latihanday10.model.GeneralModel

class Adapter(val daerahList: MutableList<GeneralModel>, val viewInteraction: ViewInteraction): RecyclerView.Adapter<Adapter.DaerahViewHolder>() {
    class DaerahViewHolder(val binding: ItemProvinsiBinding): RecyclerView.ViewHolder(binding.root) {}

    interface ViewInteraction {
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaerahViewHolder {
        return DaerahViewHolder(ItemProvinsiBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return daerahList.size
    }

    override fun onBindViewHolder(holder: DaerahViewHolder, position: Int) {
        val daerah = daerahList[position]
        holder.binding.tvProvinsi.text = daerah.nama
        holder.binding.root.setOnClickListener {
            viewInteraction.onClick(position)
        }
    }

}