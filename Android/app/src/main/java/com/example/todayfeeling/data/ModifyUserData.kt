package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class ModifyUserData(
    @SerializedName("userId")
    val id: String,
    @SerializedName("userName")
    val name: String,
    @SerializedName("userSex")
    val sex: String,
    @SerializedName("email")
    val email: String,
)
