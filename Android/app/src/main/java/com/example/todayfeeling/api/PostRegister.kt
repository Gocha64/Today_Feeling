package com.example.todayfeeling.api

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.data.SignUpData
import com.example.todayfeeling.network.RetrofitImpl
import com.example.todayfeeling.sign.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostRegister {

    fun signUpRegister(
        id: String,
        pw: String,
        name: String,
        sex: String,
        email: String,
        anger: String,
        fear: String,
        happiness: String,
        sadness: String,
        surprise: String,
        context: Context
    ) {
        val signupData =
            SignUpData(id, pw, name, sex, email, anger, fear, happiness, sadness, surprise)

        val call = RetrofitImpl.service.postSignUp(signupData)
        val dialog = AlertDialog.Builder(context)
            .setTitle("회원가입 시도중")
            .setMessage("잠시만 기다려주세요.")
            .create()
        dialog.show()

        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                Log.d("signup", "$response")
                if (response.body()?.result.toString() == "success") {
                    dialog.dismiss()
                    Log.d("test", "${response.headers().toString()}")
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                } else {
                    dialog.dismiss()
                    Log.d("test", "${response.body()}")
                    Log.d("test", "무엇인가 잘못됨")
                }
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                Log.d("test", "$t")
                Log.d("test", "무엇인가 잘못됨1111111")
            }

        })

    }
}