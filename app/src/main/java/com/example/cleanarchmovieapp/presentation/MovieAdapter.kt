package com.example.cleanarchmovieapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.databinding.MovieItemBinding
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.squareup.picasso.Picasso

class MovieAdapter(private var movieList: List<MovieEntity>, private val listener: SetOnItemClicked): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateList(newMovie: List<MovieEntity>) {
        movieList = newMovie
        notifyItemChanged(movieList.size)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val allViews = holder.binding
        val data = movieList[position]

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

    fun getId(position: Int): Int = movieList[position].id

}