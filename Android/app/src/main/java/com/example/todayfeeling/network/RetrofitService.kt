package com.example.todayfeeling.network

import com.example.todayfeeling.data.LoginData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST ("member/login")
    fun postSignIn (@Body req:LoginData) : Call<String>
}