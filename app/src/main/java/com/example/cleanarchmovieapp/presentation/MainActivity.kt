package com.example.cleanarchmovieapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchmovieapp.MovieApp
import com.example.cleanarchmovieapp.R
import com.example.cleanarchmovieapp.Utils
import com.example.cleanarchmovieapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MovieAdapter.SetOnItemClicked {
    private lateinit var binding: ActivityMainBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var vmFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MovieApp).componentPenghubung.injectMain(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRefresh()
        if (!checkInternet()) return
        initAll()
    }

    private fun checkInternet(): Boolean {
        return if (!noInternet()) { setError(); false } else true
    }

    private fun setError() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show()
    }

    private fun initRefresh() {
        binding.swiperefresh.setOnRefreshListener {
            binding.swiperefresh.isRefreshing = false
            if (!checkInternet()) return@setOnRefreshListener
            initAll()
        }
    }

    private fun noInternet(): Boolean = Utils.isNetworkAvailable(this)

    private fun initAll() {
        initVm()
        initRecView()
        setObserver()
    }

    private fun initVm() {
        viewModel = ViewModelProvider(this, vmFactory)[MainViewModel::class.java]
    }

    private fun setObserver() {
        lifecycleScope.launch {
            viewModel.getPopularMovie(Utils.ONLINE).collectLatest {
                /*if (it.errorMsg.isNotBlank()) {
                    Toast.makeText(this, it.errorMsg, Toast.LENGTH_SHORT).show()
                    return@collectLatest
                }*/
                //TODO kirim ke db

                movieAdapter.submitData(lifecycle, it)
            }
        }
    }

    private fun initRecView() {
        binding.rvPopularListMovie.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        movieAdapter = MovieAdapter(this)
        binding.rvPopularListMovie.adapter = movieAdapter
    }

    override fun onItemClicked(position: Int) {
        Utils.initIntent(this, MovieDetailsActivity::class.java, movieAdapter.getId(position))
    }
}