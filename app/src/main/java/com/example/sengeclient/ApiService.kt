package com.example.sengeclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object{
        public val API_URL = "http://74b425ad1eaf.ngrok.io "
    }
    @GET("v1/pad")
    fun get_pad(@Query("format") json:String): Call<List<padInfo>>
}