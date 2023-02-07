package com.example.todayfeeling.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitImpl {
    private const val URL = "https://0.0.0.1:8080"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RetrofitService = retrofit.create(RetrofitService::class.java)
}