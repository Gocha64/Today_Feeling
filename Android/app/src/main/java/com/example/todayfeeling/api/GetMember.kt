package com.example.todayfeeling.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.data.UserData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMember {

    fun searchMember(session: String, context: Context) {
        val call = RetrofitImpl.service.getUser(session)

        call.enqueue(object: Callback<UserData>{
            override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                if (response.isSuccessful){
                    val sharedPreference = context.getSharedPreferences("user", 0)
                    val editor = sharedPreference.edit()
                    editor.putString("id", response.body()?.userId)
                    editor.putString("name", response.body()?.userName)
                    editor.putString("sex", response.body()?.userSex)
                    editor.putString("email", response.body()?.email)
                    editor.putString("anger", response.body()?.anger)
                    editor.putString("fear", response.body()?.fear)
                    editor.putString("happiness", response.body()?.happiness)
                    editor.putString("sadness", response.body()?.sadness)
                    editor.putString("surprise", response.body()?.surprise)
                    editor.apply()
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
                else{
                    Log.d("test", response.body().toString())
                }
            }

            override fun onFailure(call: Call<UserData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}