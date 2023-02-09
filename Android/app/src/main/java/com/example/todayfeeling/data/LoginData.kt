package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("userId")
    var id:String,
    @SerializedName("userPw")
    var password:String
)
