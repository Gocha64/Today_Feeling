package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.Date

data class EmotionData(
    @SerializedName("result")
    val result: ArrayList<Emotion>
)

data class Emotion(
    @SerializedName("datetime")
    val dateTime: String,
    @SerializedName("timestamp")
    val time: String,
    @SerializedName("emotion")
    val emotion: Int,
    @SerializedName("count")
    val count: Int,
    @SerializedName("songUrl")
    val songUrl: String
)
