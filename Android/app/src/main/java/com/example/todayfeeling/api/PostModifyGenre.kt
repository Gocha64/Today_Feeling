package com.example.todayfeeling.api

import com.example.todayfeeling.data.ModifyGenreData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostModifyGenre {
    fun modifyGenre(
        anger: String,
        fear: String,
        happiness: String,
        sadness: String,
        surprise: String,
        session: String
    ) {
        val call = RetrofitImpl.service.modifyGenre(session, ModifyGenreData(anger, fear, happiness, sadness, surprise))

        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                if (response.isSuccessful) {
                    //수정완료후 화면 이동 만들기
                }
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}