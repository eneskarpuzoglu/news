package com.kar.enes.app.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kar.enes.app.data.model.ArticleModel

/**
 * Created by M.Enes on 1/2/2020.
 */
class HeadLinesResponse {
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("totalResults")
    @Expose
    var totalResults: Int? = null
    @SerializedName("articles")
    @Expose
    var articles: ArrayList<ArticleModel>? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}