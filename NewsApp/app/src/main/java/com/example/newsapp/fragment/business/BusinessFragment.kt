package com.example.newsapp.fragment.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.SourceNewsAdapter
import com.example.newsapp.databinding.FragmentBusinessBinding
import com.example.newsapp.model.SourceNewsModel
import com.example.newsapp.util.EndlessScrollListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class BusinessFragment : Fragment() {

    companion object {
        fun newInstance() = BusinessFragment()
    }

    private lateinit var viewModel: BusinessViewModel
    private var page = 1
    private var adapterSource : SourceNewsAdapter?=null
    private var sourceLiveData = ArrayList<SourceNewsModel.SourcesEntity>()
    private lateinit var bindingFragment:FragmentBusinessBinding
    var job: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingFragment = FragmentBusinessBinding.inflate(inflater,container,false)
        var view = bindingFragment.root
        adapterSource = SourceNewsAdapter(requireContext())
        viewModel = ViewModelProvider(this).get(BusinessViewModel::class.java)
        bindingFragment.loading.show()
        viewModel.getSourceNews(page)
        viewModel.observeSourceLiveData().observe(viewLifecycleOwner, Observer { sourceList ->
            println("Response ${sourceList[0].id}")
                        sourceLiveData.clear()
            sourceLiveData.addAll(sourceList)
            adapterSource!!.setPost(sourceList as ArrayList<SourceNewsModel.SourcesEntity>)
            bindingFragment.loading.hide()
        })
        bindingFragment.rvList.layoutManager = LinearLayoutManager(requireContext())
        bindingFragment.rvList.adapter = adapterSource
        bindingFragment.etSearch.addTextChangedListener { editable ->
            bindingFragment.loading.show()
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        adapterSource!!.filter.filter(editable.toString())
                        bindingFragment.rvList.scrollToPosition(0)
                        bindingFragment.loading.hide()
                    }
                }
            }
        }
        val scrollListener = object : EndlessScrollListener() {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                println("page $page")
                viewModel.getSourceNews(page)
                viewModel.observeSourceLiveData().observe(viewLifecycleOwner, Observer { sourceList ->
//                    println("Response ${sourceList[0].id}")
                    sourceLiveData.clear()
                    sourceLiveData.addAll(sourceList)
                    adapterSource!!.setPost(sourceList as ArrayList<SourceNewsModel.SourcesEntity>)
                })
            }
        }
        bindingFragment.rvList.addOnScrollListener(scrollListener)
        return view
    }


}