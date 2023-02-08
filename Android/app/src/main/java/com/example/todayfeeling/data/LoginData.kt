package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("name")
    var id:String,
    @SerializedName("pw")
    var password:String
)
