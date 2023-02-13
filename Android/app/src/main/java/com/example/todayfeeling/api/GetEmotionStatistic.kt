package com.example.todayfeeling.api

import com.example.todayfeeling.data.EmotionData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetEmotionStatistic {
    fun emotionStatistic(session: String) {
        val call = RetrofitImpl.service.dayEmotionStatistic(session)

        call.enqueue(object : Callback<EmotionData> {
            override fun onResponse(call: Call<EmotionData>, response: Response<EmotionData>) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<EmotionData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}