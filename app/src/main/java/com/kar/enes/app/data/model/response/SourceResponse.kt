package com.kar.enes.app.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.kar.enes.app.data.model.SourceModel

/**
 * Created by M.Enes on 1/2/2020.
 */
class SourceResponse {
    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("sources")
    @Expose
    var sources: ArrayList<SourceModel>? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null
}