package com.example.todayfeeling.api

import android.util.Log
import com.example.todayfeeling.data.EmotionRecommend
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.data.result
import com.example.todayfeeling.listener.PostRecommendUrl
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRecommend(private val listener: PostRecommendUrl) {
    fun recommendSong(emotion:EmotionRecommend) {
        val call = RetrofitImpl.service.recommendEmotion(emotion)

        call.enqueue(object : Callback<result> {
            override fun onResponse(call: Call<result>, response: Response<result>) {
                if (response.isSuccessful) {
                    if (response.body()!!.result != "undefined error") {
                        Log.e("test", "들어옴")
                        listener.postRecommendUrlListener(response.body()!!.result)
                    } else{
                        Log.e("test", response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<result>, t: Throwable) {
                Log.e("test","서버오류 발생")
            }

        })
    }
}