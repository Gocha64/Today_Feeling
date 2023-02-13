package com.example.todayfeeling.api

import com.example.todayfeeling.data.ModifyUserData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostModifyUser {
    fun modifyUser(name:String, id:String, sex:String, email:String, session:String) {
        val call = RetrofitImpl.service.modifyUser(session, ModifyUserData(id, name, sex, email))
        call.enqueue(object : Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}