package com.kar.enes.app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by M.Enes on 1/2/2020.
 */
class ArticleModel {
    @SerializedName("source")
    @Expose
    var source: SourceModel? = null
    @SerializedName("author")
    @Expose
    var author: String = ""
    @SerializedName("title")
    @Expose
    var title: String = ""
    @SerializedName("description")
    @Expose
    var description: String = ""
    @SerializedName("url")
    @Expose
    var url: String = ""
    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String = ""
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String = ""
    @SerializedName("content")
    @Expose
    var content: String = ""
}