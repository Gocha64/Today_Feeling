package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("id")
    val userId: String,
    @SerializedName("sex")
    val userSex: String,
    @SerializedName("name")
    val userName: String,
    @SerializedName("email")
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
    val surprise: String
)
