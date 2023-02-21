package com.example.todayfeeling.data

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.Date

data class EmotionData(
    @SerializedName("result")
    val result: Emotion,
    @SerializedName("result_f")
    val result_f: String
)

data class Emotion(
    @SerializedName("count")
    val count: Int,
    @SerializedName("angerCount")
    val anger: Int,
    @SerializedName("fearCount")
    val fear: Int,
    @SerializedName("happinessCount")
    val happy: Int,
    @SerializedName("sadnessCount")
    val sad: Int,
    @SerializedName("surpriseCount")
    val surprise: Int
)

data class EmotionRecommend(
    @SerializedName("emotion_data")
    val emotion: Int
)

data class result(
    @SerializedName("result")
    val result: String
)

data class EmotionDetailData(
    @SerializedName("result")
    val result: ArrayList<EmotionDetail>,
    @SerializedName("result_f")
    val result_f: String
)

data class EmotionDetail(
    @SerializedName("datetime")
    val date: String,
    @SerializedName("emotion")
    val emotion: Int,
    @SerializedName("songUrl")
    val song: String,
    @SerializedName("timestamp")
    val time: String,
)
