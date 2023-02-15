package com.example.todayfeeling.api

import android.content.Context
import com.example.todayfeeling.data.ModifyGenreData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.listener.AlertUserModify
import com.example.todayfeeling.listener.AlertUserModifyGenre
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostModifyGenre(context: Context, listener: AlertUserModifyGenre) {
    val context = context
    val listen = listener
    fun modifyGenre(
        anger: String,
        fear: String,
        happiness: String,
        sadness: String,
        surprise: String,
    ) {
        val call = RetrofitImpl.service.modifyGenre(ModifyGenreData(anger, fear, happiness, sadness, surprise))

        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                if (response.isSuccessful) {
                    val sharedPreference = context.getSharedPreferences("user", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("anger", anger)
                    editor.putString("fear", fear)
                    editor.putString("happiness", happiness)
                    editor.putString("sadness", sadness)
                    editor.putString("surprise", surprise)
                    listen.alertUserModifyGenre()
                }
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}