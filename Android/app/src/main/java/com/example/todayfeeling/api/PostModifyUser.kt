package com.example.todayfeeling.api

import android.content.Context
import android.util.Log
import com.example.todayfeeling.data.ModifyUserData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.listener.AlertUserModify
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostModifyUser(context: Context, listener: AlertUserModify) {
    val context = context
    val listen = listener
    fun modifyUser(id:String, pw:String, name:String, sex:String, email:String) {
        val call = RetrofitImpl.service.modifyUser(ModifyUserData(id, pw, name, sex, email))
        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                if (response.isSuccessful) {
                    Log.d("testing", response.body().toString())
                    val sharedPreference = context.getSharedPreferences("user", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("id", id)
                    editor.putString("name", name)
                    editor.putString("sex", sex)
                    editor.putString("email", email)
                    editor.apply()
                    listen.alertUserModify()
                }
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                Log.d("test",t.message.toString())
            }

        })
    }
}