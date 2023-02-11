package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("Uid")
    val uId: String,
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userSex")
    val userSex: String
)
