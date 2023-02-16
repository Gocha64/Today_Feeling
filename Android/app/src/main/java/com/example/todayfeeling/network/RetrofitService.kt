package com.example.todayfeeling.network

import com.example.todayfeeling.data.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.sql.Date
import java.time.LocalDateTime

interface RetrofitService {
    @POST("member/login")
    fun postSignIn(@Body req: LoginData): Call<ResultData>

    @POST("member/register")
    fun postSignUp(@Body req: SignUpData): Call<ResultData>

    @GET("member/search")
    fun getUser(): Call<UserData>

    @POST("member/modify/info")
    fun modifyUser(@Body req: ModifyUserData): Call<ResultData>

    @POST("emotion/recommend")
    fun recommendEmotion(@Header("session") session:String, @Body emotion: Int): Call<ResultData>

    @POST("member/modify/genre")
    fun modifyGenre(@Body req: ModifyGenreData): Call<ResultData>

    @GET("emotion/info_day")
    fun dayEmotionStatistic(@Query("date") req: String): Call<EmotionData>

    @GET("emotion/info_week")
    fun weekEmotionStatistic(): Call<EmotionData>

    @GET("emotion/info_month")
    fun monthEmotionStatistic(): Call<EmotionData>
}