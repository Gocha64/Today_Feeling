package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName

data class ModifyGenreData(
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
