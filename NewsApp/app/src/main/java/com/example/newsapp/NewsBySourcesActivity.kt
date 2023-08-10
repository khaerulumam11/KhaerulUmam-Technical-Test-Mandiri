

package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.ActivityNewsBySourcesBinding
import com.example.newsapp.fragment.technology.TechnologyViewModel
import com.example.newsapp.model.NewsModel
import com.example.newsapp.model.SourceNewsModel
import com.example.newsapp.util.EndlessScrollListener
import com.example.newsapp.viewModel.NewsBySourcesViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsBySourcesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsBySourcesBinding
    private lateinit var viewModel: NewsBySourcesViewModel
    private var adapter : NewsAdapter?=null
    var job: Job? = null
    var page = 1
    var sources =""
    var search = ""
    private var newsLiveData = ArrayList<NewsModel.ArticlesEntity>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBySourcesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = intent.getStringExtra("name")
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.white))
        sources = intent.getStringExtra("id")!!.toLowerCase()
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_white_24)
        binding.toolbar.setNavigationOnClickListener { finish() }
        adapter = NewsAdapter(this@NewsBySourcesActivity)
        viewModel = ViewModelProvider(this).get(NewsBySourcesViewModel::class.java)
        binding.loading.show()
        viewModel.getNews(page,sources,search)
        viewModel.observeSourceLiveData().observe(this, Observer { newsList ->
            println("Data "+newsList.size)
            newsLiveData.clear()
            newsLiveData.addAll(newsList)
            adapter!!.setPost(newsList as ArrayList<NewsModel.ArticlesEntity>)
            binding.loading.show()
        })
        binding.rvList.layoutManager = LinearLayoutManager(this@NewsBySourcesActivity)
        binding.rvList.adapter = adapter
        binding.etSearch.addTextChangedListener { editable ->
            binding.loading.show()
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        search = editable.toString()
                        viewModel.getNews(page,sources,search)
                        viewModel.observeSourceLiveData().observe(this@NewsBySourcesActivity, Observer { sourceList ->
//                    println("Response ${sourceList[0].id}")
                            newsLiveData.clear()
                            newsLiveData.addAll(sourceList)
                            adapter!!.setPost(sourceList as java.util.ArrayList<NewsModel.ArticlesEntity>)
                            binding.loading.hide()
                        })
                        binding.rvList.scrollToPosition(0)
                    }
                }
            }
        }
//        val scrollListener = object : EndlessScrollListener() {
//            override fun onLoadMore(page: Int, totalItemsCount: Int) {
//                println("page $page")
//                viewModel.getNews(page,sources,search)
//                viewModel.observeSourceLiveData().observe(this@NewsBySourcesActivity, Observer { sourceList ->
////                    println("Response ${sourceList[0].id}")
//                    newsLiveData.clear()
//                    newsLiveData.addAll(sourceList)
//                    adapter!!.setPost(sourceList as java.util.ArrayList<NewsModel.ArticlesEntity>)
//                })
//            }
//        }
//        binding.rvList.addOnScrollListener(scrollListener)
    }
}