package com.example.myapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object{
        public val API_URL = "http://7bbc29ba7e82.ngrok.io"
    }
    @GET("v1/pad")
    fun get_pad(@Query("format") json:String): Call<List<padInfo>>
}