package com.example.newsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SourceNewsModel {
    @Expose
    @SerializedName("sources")
    var sources: List<SourcesEntity>? = null

    @Expose
    @SerializedName("status")
    var status: String? = null

    class SourcesEntity {
        @Expose
        @SerializedName("country")
        var country: String? = null

        @Expose
        @SerializedName("language")
        var language: String? = null

        @Expose
        @SerializedName("category")
        var category: String? = null

        @Expose
        @SerializedName("url")
        var url: String? = null

        @Expose
        @SerializedName("description")
        var description: String? = null

        @Expose
        @SerializedName("name")
        var name: String? = null

        @Expose
        @SerializedName("id")
        var id: String? = null
    }
}