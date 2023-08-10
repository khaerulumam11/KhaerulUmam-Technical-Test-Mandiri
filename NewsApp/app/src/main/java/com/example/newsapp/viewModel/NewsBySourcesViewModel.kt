package com.example.newsapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.newsapp.model.NewsModel
import com.example.newsapp.model.SourceNewsModel
import com.example.newsapp.remote.DatabaseAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

class NewsBySourcesViewModel : ViewModel() {
    private var newsLiveData = MutableLiveData<List<NewsModel.ArticlesEntity>>()
    fun getNews(page:Int, sources:String,search:String) {
        AndroidNetworking.get("${DatabaseAPI.BASE_API_URL}/top-headlines?q=$search&sources=$sources&page=$page&apiKey=${DatabaseAPI.API_KEY}")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    println("Response Detail $response")
                    var gsonBuilder = GsonBuilder().serializeNulls()
                    var gson: Gson? =  gsonBuilder.create()
                    var sourceNewsResp: NewsModel = gson!!.fromJson(
                        response.toString(),
                        NewsModel::class.java
                    )
                    newsLiveData.value = sourceNewsResp.articles
                }

                override fun onError(anError: ANError) {
                    println("Erorr " + anError.message)
                }
            })


    }
    fun observeSourceLiveData() : LiveData<List<NewsModel.ArticlesEntity>> {
        return newsLiveData
    }
}