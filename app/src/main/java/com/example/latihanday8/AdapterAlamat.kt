package com.example.latihanday8

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.latihanday8.databinding.AlamatItemBinding

class AdapterAlamat(var listOfAlamat: MutableList<AlamatModel>, var listenerUtility: Utility):
    RecyclerView.Adapter<AdapterAlamat.AlamatViewHolder>() {

    interface Utility {
        fun onUbahItemListener(position: Int)
        fun onDeleteItemListener(position: Int)
    }

    class AlamatViewHolder(val binding: AlamatItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlamatViewHolder {
        return AlamatViewHolder(AlamatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))//AlamatViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.alamat_item, parent, false))
    }

    override fun getItemCount(): Int {
        return listOfAlamat.size
    }

    override fun onBindViewHolder(holder: AlamatViewHolder, position: Int) {
        with(holder) {
            with(listOfAlamat[position]) {
                binding.tvLabel.text = this.label
                binding.tvLabelDanTelp.text = this.getLabelTelp()
                binding.tvDetailAlamat.text = this.detailAlamat
                binding.tvDetailKota.text = this.detailAlamat
                binding.switchItemAlamat.isChecked = this.checked

                binding.btnHapus.setOnClickListener {
                    listenerUtility.onDeleteItemListener(position)
                }
                binding.btnUbah.setOnClickListener {
                    listenerUtility.onUbahItemListener(position)
                }
            }
        }
    }
}