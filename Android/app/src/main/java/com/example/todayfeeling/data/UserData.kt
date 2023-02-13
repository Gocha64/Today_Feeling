package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("userSex")
    val userSex: String,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("userEmail")
    val email: String,
    @SerializedName("anger")
    val anger: String,
    @SerializedName("fear")
    val fear: String,
    @SerializedName("happiness")
    val happiness: String,
    @SerializedName("sadness")
    val sadness: String,
    @SerializedName("surprise")
    val surprise: String,
)
