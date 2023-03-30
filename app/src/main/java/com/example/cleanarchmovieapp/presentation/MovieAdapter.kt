package com.example.cleanarchmovieapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.databinding.MovieItemBinding
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.squareup.picasso.Picasso

class MovieAdapter(private val listener: SetOnItemClicked): PagingDataAdapter<MovieEntity, MovieAdapter.MovieViewHolder>(DiffCallback) {
    companion object {
        object DiffCallback : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(
                oldItem: MovieEntity,
                newItem: MovieEntity
            ) = oldItem == newItem
        }
    }

    class MovieViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val allViews = holder.binding
        val data = getItem(position) ?: return

        Picasso.get()
            .load(Utils.BASE_IMAGE_URL + data.image)
            .resize(300, 300)
            .into(allViews.imgMovie)

        allViews.tvMovieTitle.text = data.name
        allViews.tvRating.text = data.rating.toString()
        allViews.tvYear.text = data.year

        //allViews.tvRating.setBackgroundColor(data.setColor())

        allViews.root.setOnClickListener {
            listener.onItemClicked(position)
        }
    }

    interface SetOnItemClicked {
        fun onItemClicked(position: Int)
    }

    fun getId(position: Int): Int = getItem(position)?.id ?: 0

}