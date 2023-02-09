package com.example.todayfeeling.network

import com.example.todayfeeling.data.LoginData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.data.SignUpData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Part

interface RetrofitService {
    @POST ("member/login")
    fun postSignIn (@Body req:LoginData) : Call<ResultData>

    @POST("member/register")
    fun postSignUp (@Body req: SignUpData): Call<ResultData>
}