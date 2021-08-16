package com.example.sengeclient

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object{
        public val API_URL = "http://127.0.0.1:8000/v1/"
    }
    @GET("pad")
    fun get_pad(@Query("format") json:String): Call<List<padInfo>>
}