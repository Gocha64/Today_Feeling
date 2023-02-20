package com.example.todayfeeling.listener

import com.example.todayfeeling.data.EmotionData

interface GetStatistic {
    fun getDayStatistic(data: EmotionData)
}