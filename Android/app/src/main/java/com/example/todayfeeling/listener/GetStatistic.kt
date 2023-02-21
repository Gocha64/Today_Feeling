package com.example.todayfeeling.listener

import com.example.todayfeeling.data.EmotionData
import com.example.todayfeeling.data.EmotionDetail

interface GetStatistic {
    fun getDayStatistic(data: EmotionData)

    fun getDayDetailStatistic(data: ArrayList<EmotionDetail>)
}