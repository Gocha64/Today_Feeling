package com.example.todayfeeling.network

import com.example.todayfeeling.data.LoginData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.data.SignUpData
import com.example.todayfeeling.data.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
    @POST ("member/login")
    fun postSignIn (@Body req:LoginData) : Call<ResultData>

    @POST("member/register")
    fun postSignUp (@Body req: SignUpData): Call<ResultData>

    @GET("member/search")
    fun getUser(@Header("session") req: String): Call<UserData>
}