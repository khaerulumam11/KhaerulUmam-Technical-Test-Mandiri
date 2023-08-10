package com.example.newsapp.fragment.business

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.newsapp.model.SourceNewsModel
import com.example.newsapp.remote.DatabaseAPI.API_KEY
import com.example.newsapp.remote.DatabaseAPI.BASE_API_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject

class BusinessViewModel : ViewModel() {
    private var sourceLiveData = MutableLiveData<List<SourceNewsModel.SourcesEntity>>()
    fun getSourceNews(page:Int) {
        AndroidNetworking.get("$BASE_API_URL/top-headlines/sources?&category=business&page=$page&apiKey=$API_KEY")
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    println("Response $response")
                    var gsonBuilder = GsonBuilder().serializeNulls()
                    var gson: Gson? =  gsonBuilder.create()
                    var sourceNewsResp: SourceNewsModel = gson!!.fromJson(
                        response.toString(),
                        SourceNewsModel::class.java
                    )
                    sourceLiveData.value = sourceNewsResp.sources
                    println("Response Data "+ sourceNewsResp.sources?.get(0)!!.id)
                }

                override fun onError(anError: ANError) {
                    println("Erorr " + anError.message)
                }
            })


    }
    fun observeSourceLiveData() : LiveData<List<SourceNewsModel.SourcesEntity>> {
        return sourceLiveData
    }


}