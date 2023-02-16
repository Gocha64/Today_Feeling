package com.example.todayfeeling.api

import android.util.Log
import com.example.todayfeeling.data.EmotionData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class GetEmotionStatistic {
    fun dayEmotionStatistic(day: String) {
        val call = RetrofitImpl.service.dayEmotionStatistic(day)

        call.enqueue(object : Callback<EmotionData> {
            override fun onResponse(call: Call<EmotionData>, response: Response<EmotionData>) {
                Log.d("testing", response.body().toString())
                if (response.isSuccessful) {
                }
            }

            override fun onFailure(call: Call<EmotionData>, t: Throwable) {
                Log.d("test",t.cause.toString())
            }

        })
    }

    fun weekEmotionStatistic(session: String) {
        val call = RetrofitImpl.service.weekEmotionStatistic()

        call.enqueue(object : Callback<EmotionData> {
            override fun onResponse(call: Call<EmotionData>, response: Response<EmotionData>) {
                TODO("Not yet implemented")
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<EmotionData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun monthEmotionStatistic(session: String) {
        val call = RetrofitImpl.service.monthEmotionStatistic()

        call.enqueue(object : Callback<EmotionData> {
            override fun onResponse(call: Call<EmotionData>, response: Response<EmotionData>) {
                TODO("Not yet implemented")
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<EmotionData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}