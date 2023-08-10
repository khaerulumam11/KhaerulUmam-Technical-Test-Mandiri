package com.example.movieapps

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapps.adapter.AdapterGenre
import com.example.movieapps.adapter.AdapterMovie
import com.example.movieapps.databinding.ActivityMainBinding
import com.example.movieapps.model.GenreModel
import com.example.movieapps.model.MovieModel
import com.example.movieapps.viewModel.MainViewModel
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel : MainViewModel
    private var adapterMovie : AdapterMovie?=null
    private var adapterGenre : AdapterGenre?=null
    private var movieLiveData = ArrayList<MovieModel.ResultsEntity>()
    private var genreLiveData = ArrayList<GenreModel.GenresEntity>()
    var page = 1
    var loading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = "Home"
        binding.loading.show()
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        adapterMovie = AdapterMovie(this)
        adapterGenre = AdapterGenre(this)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getDiscoverMovie(page)
        viewModel.observeMovieLiveData().observe(this, Observer { movieList ->
            movieLiveData.clear()
            movieLiveData.addAll(movieList)
            adapterMovie!!.setPost(movieList as ArrayList<MovieModel.ResultsEntity>)
            binding.loading.hide()
        })
        viewModel.getGenreMovie()
        viewModel.observeGenreLiveData().observe(this, Observer { genreList ->
            genreLiveData.clear()
            genreLiveData.addAll(genreList)
            adapterGenre!!.setPost(genreList as ArrayList<GenreModel.GenresEntity>)
            binding.loading.hide()
        })
        binding.discoverRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        binding.discoverRecyclerView.adapter = adapterMovie

        binding.genreRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL,false)
        binding.genreRecyclerView.adapter = adapterGenre

        binding.discoverRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!loading) {
                    if (linearLayoutManager!!.findLastCompletelyVisibleItemPosition() == movieLiveData.size - 1) {
                        //bottom of list!

                        movieLiveData.clear()
                        binding.loading.show()
                        page+=1
                        viewModel.getDiscoverMovie(page)
                        viewModel.observeMovieLiveData().observe(this@MainActivity, Observer { movieList ->
                            adapterMovie!!.setPost(movieList as ArrayList<MovieModel.ResultsEntity>)
                            binding.loading.hide()
                        })
                        binding.discoverRecyclerView.post { // There is no need to use notifyDataSetChanged()
                            adapterMovie!!.notifyItemChanged(movieLiveData.size-1)
                        }
                        loading = true
                    }
                } else {
                    if (linearLayoutManager!!.findFirstCompletelyVisibleItemPosition() == 0) {

                        movieLiveData.clear()
                        binding.loading.show()
                        page-=1
                        viewModel.getDiscoverMovie(page)
                        viewModel.observeMovieLiveData().observe(this@MainActivity, Observer { movieList ->
                            adapterMovie!!.setPost(movieList as ArrayList<MovieModel.ResultsEntity>)
                            binding.loading.hide()
                        })
                        binding.discoverRecyclerView.post { // There is no need to use notifyDataSetChanged()
                            adapterMovie!!.notifyItemChanged(movieLiveData.size-1)
                        }
                        loading = false
                    }
                }
            }
        })
    }

}