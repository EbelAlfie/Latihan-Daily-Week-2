package com.example.cleanarchmovieapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MovieAdapter.SetOnItemClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initRecView()
        setObserver()
    }

    private fun setObserver() {
        viewModel.getPopularMovieData().observe(this) {
            if (it == null) return@observe
            if (it.errorMsg.isNotBlank()) {
                Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                return@observe
            }
            movieAdapter.updateList(it.result)
        }
    }

    private fun initRecView() {
        binding.rvPopularListMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movieAdapter = MovieAdapter(mutableListOf(), this)
        binding.rvPopularListMovie.adapter = movieAdapter
    }

    private fun initViewModel() {
        val factory = ViewModelFactory.getInstance()
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onItemClicked(position: Int) {
        Utils.initIntent(this, MovieDetailsActivity::class.java, movieAdapter.getId(position))
    }
}