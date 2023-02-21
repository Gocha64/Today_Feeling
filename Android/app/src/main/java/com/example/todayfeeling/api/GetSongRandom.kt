package com.example.todayfeeling.api

import android.util.Log
import com.example.todayfeeling.data.result
import com.example.todayfeeling.listener.PostRecommendUrl
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSongRandom(private val listener: PostRecommendUrl) {
    fun getSongRandom() {
        val call = RetrofitImpl.service.recommendSong()

        call.enqueue(object : Callback<result>{
            override fun onResponse(call: Call<result>, response: Response<result>) {
                if (response.isSuccessful) {
                    if (response.body()!!.result != "undefined error") {
                        listener.postRecommendUrlListener(response.body()!!.result)
                    } else {
                        Log.e("random", response.body()!!.result)
                    }
                }
            }

            override fun onFailure(call: Call<result>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}