package com.example.todayfeeling.network

import com.example.todayfeeling.data.*
import okhttp3.Cookie
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
interface RetrofitService {
    @POST("member/login")
    fun postSignIn(@Body req: LoginData): Call<ResultData>

    @POST("member/register")
    fun postSignUp(@Body req: SignUpData): Call<ResultData>

    @GET("member/search")
    fun getUser(): Call<UserData>

    @POST("member/modify")
    fun modifyUser(@Header("session") session:String, @Body req: ModifyUserData): Call<ResultData>

    @POST("emotion/recommend")
    fun recommendEmotion(@Header("session") session:String, @Body emotion: Int): Call<ResultData>

    @POST("member/modify/genre")
    fun modifyGenre(@Header("session") session:String, @Body req: ModifyGenreData): Call<ResultData>

    @GET("emotion/info_day")
    fun dayEmotionStatistic(@Header("session") session:String): Call<EmotionData>

    @GET("emotion/info_week")
    fun weekEmotionStatistic(@Header("session") session:String): Call<EmotionData>

    @GET("emotion/info_month")
    fun monthEmotionStatistic(@Header("session") session:String): Call<EmotionData>
}