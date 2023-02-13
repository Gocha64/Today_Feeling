package com.example.todayfeeling.api

import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRecommend {
    fun recommendSong(emotion:Int, session:String) {
        val call = RetrofitImpl.service.recommendEmotion(session, emotion)

        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                if (response.isSuccessful) {

                }
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}