package com.example.myapp

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    companion object{
        public val API_URL = "http://7b8b-218-154-215-121.ngrok.io"
    }
    @GET("v1/pad")
    fun get_pad(@Query("format") json:String): Call<List<padInfo>>

    @GET("v1/pad")
    fun get_padid(@Query("format") json:String): Call<List<padlist>>

    @GET("v1/pad/{id}/")
    fun get_id(
            @Path("id") id:Int):Call<padlist>

    @POST("v1/review/")
    fun reg_review(@Body review:p_reviewInfo):Call<reviewInfo>

    @GET("v1/review/")
    fun get_review(@Query("format") json:String):Call<List<reviewInfo>>
}