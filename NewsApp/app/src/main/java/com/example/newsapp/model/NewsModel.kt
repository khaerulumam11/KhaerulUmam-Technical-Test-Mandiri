package com.example.newsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class NewsModel {
    @Expose
    @SerializedName("articles")
    var articles: List<ArticlesEntity>? = null

    @Expose
    @SerializedName("totalResults")
    var totalResults = 0

    @Expose
    @SerializedName("status")
    var status: String? = null

    class ArticlesEntity {
        @Expose
        @SerializedName("content")
        var content: String? = null

        @Expose
        @SerializedName("publishedAt")
        var publishedAt: String? = null

        @Expose
        @SerializedName("urlToImage")
        var urlToImage: String? = null

        @Expose
        @SerializedName("url")
        var url: String? = null

        @Expose
        @SerializedName("description")
        var description: String? = null

        @Expose
        @SerializedName("title")
        var title: String? = null

        @Expose
        @SerializedName("author")
        var author: String? = null

        @Expose
        @SerializedName("source")
        var source: SourceEntity? = null
    }

    class SourceEntity {
        @Expose
        @SerializedName("name")
        var name: String? = null

        @Expose
        @SerializedName("id")
        var id: String? = null
    }
}