package com.kar.enes.app.data.remote

import com.kar.enes.app.data.model.request.LoginReq
import com.kar.enes.app.data.model.response.HeadLinesResponse
import com.kar.enes.app.data.model.response.SourceResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by M.Enes on 5/8/2019
 */
interface ApiHelper {

    @GET("v2/sources")
    fun getSources(@Query("apiKey") id: String ): Observable<SourceResponse>

    @GET("v2/top-headlines")
    fun getTopHeadlines(@Query("apiKey") id: String, @Query("sources") sourceId: String ): Observable<HeadLinesResponse>
}