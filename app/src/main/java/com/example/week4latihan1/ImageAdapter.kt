package com.example.week4latihan1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week4latihan1.databinding.ImageItemBinding
import com.squareup.picasso.Picasso

class ImageAdapter(val imageItem: MutableList<String>): RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){
    class ImageViewHolder(val binding: ImageItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return imageItem.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Picasso.get()
            .load(imageItem[position])
            .resize(300, 300)
            .into(holder.binding.ivGambarProdukDisplay)
    }

    fun insertItem(gambarProduk: MutableList<String>) {
        imageItem.addAll(gambarProduk)
        notifyItemRangeChanged(0, imageItem.size)
    }

}