package com.example.todayfeeling.api

import android.util.Log
import com.example.todayfeeling.data.EmotionDetailData
import com.example.todayfeeling.listener.GetStatistic
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetEmotionDetailStatistic(private val listener: GetStatistic) {

    fun dayEmotionDetailStatistic(day: String) {
        val call = RetrofitImpl.service.dayEmotionDetailStatistic(day)

        call.enqueue(object : Callback<EmotionDetailData> {
            override fun onResponse(call: Call<EmotionDetailData>, response: Response<EmotionDetailData>) {
                if (response.isSuccessful) {
                    if (response.body()!!.result_f != "undefined error") {
                        listener.getDayDetailStatistic(response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<EmotionDetailData>, t: Throwable) {
                Log.d("test",t.cause.toString())
            }

        })
    }
}