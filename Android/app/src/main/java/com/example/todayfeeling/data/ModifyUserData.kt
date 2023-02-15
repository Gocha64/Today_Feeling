package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class ModifyUserData(
    @SerializedName("userId")
    val id: String,
    @SerializedName("userPw")
    val pw: String,
    @SerializedName("userName")
    val name: String,
    @SerializedName("userSex")
    val sex: String,
    @SerializedName("userEmail")
    val email: String,
)
