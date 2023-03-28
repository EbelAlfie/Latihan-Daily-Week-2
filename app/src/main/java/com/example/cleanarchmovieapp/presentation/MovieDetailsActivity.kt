package com.example.cleanarchmovieapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.data.MovieModel
import com.example.cleanarchmovieapp.databinding.ActivityMovieDetailsBinding
import com.example.cleanarchmovieapp.domain.MovieEntity
import com.squareup.picasso.Picasso


class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private var id: Int = -1
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        id = this.intent.getIntExtra(Utils.ID_KEY, -1)
        initViewModel()
        if (id != -1){
            viewModel.setMovie(id)
            setObserver()
        }
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setObserver() {
        viewModel.getSpecificMovie().observe(this){
            if (it.id == -1) return@observe
            setView(it)
        }
    }

    private fun setView(it: MovieEntity) {
        Picasso.get().load(Utils.BASE_IMAGE_URL + it.image).into(binding.imgMovie)
        binding.tvMovieTitle.text = it.name
        binding.tvYear.text = it.year
        binding.tvRating.text = it.rating.toString()
        binding.tvDescription.text = it.desc
    }
}