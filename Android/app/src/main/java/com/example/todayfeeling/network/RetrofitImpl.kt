package com.example.todayfeeling.network

import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager

object RetrofitImpl {
    private const val URL = "http://218.232.159.156:10081/"

    private val okHttpClient = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(CookieManager())).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RetrofitService = retrofit.create(RetrofitService::class.java)
}