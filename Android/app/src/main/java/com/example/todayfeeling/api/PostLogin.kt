package com.example.todayfeeling.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.data.LoginData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostLogin {
    fun startLogin(id: String, pw: String, context: Context) {
        Log.d("test", id)
        Log.d("test", pw)
        val call = RetrofitImpl.service.postSignIn(LoginData(id,pw))

        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                if (response.isSuccessful) {
                    if (response.body()?.result.toString() == "success") {
                        val sharedPreference = context.getSharedPreferences("user", 0)
                        val editor = sharedPreference.edit()
                        editor.putString("id", id)
                        editor.putString("pw", pw)
                        editor.apply()
                        Log.d("login1","${response.body().toString()}")
                        GetMember().searchMember(context)
                    }
                    else {
                        Log.d("test","${response.body().toString()}")
                        //로그인 실패 알리기
                    }
                }
            }
            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                // 서버 에러 알림창 띄우기
            }

        })
    }
}