package com.kar.enes.app.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by M.Enes on 1/2/2020.
 */
class SourceModel {
    @SerializedName("id")
    @Expose
    var id: String = ""
    @SerializedName("name")
    @Expose
    var name: String = ""
    @SerializedName("description")
    @Expose
    var description: String = ""
    @SerializedName("url")
    @Expose
    var url: String = ""
    @SerializedName("category")
    @Expose
    var category: String = ""
    @SerializedName("language")
    @Expose
    var language: String = ""
    @SerializedName("country")
    @Expose
    var country: String = ""
}