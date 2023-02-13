package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName
import java.util.Date

data class EmotionData(
    @SerializedName("result")
    val result: ArrayList<Emotion>
)

data class Emotion(
    @SerializedName("datetime")
    val dateTime: Date,
    @SerializedName("emotion")
    val emotion: Int,
    @SerializedName("songUrl")
    val songUrl: String
)
