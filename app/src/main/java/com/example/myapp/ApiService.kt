package com.example.myapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    companion object{
        public val API_URL = "http://a567-218-154-215-121.ngrok.io"
    }
    @GET("v1/pad")
    fun get_pad(@Query("format") json:String): Call<List<padInfo>>

    @GET("v1/pad")
    fun get_padid(@Query("format") json:String): Call<List<padlist>>

    @GET("v1/pad/{id}/")
    fun get_id(@Path("id") id:Int):Call<padlist>

    @GET("v1/detectionInfo/")
    fun get_detection(@Query("format") json:String):Call<ArrayList<detectioninfo>>

}