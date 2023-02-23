package com.example.todayfeeling.listener

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

interface PostRecommendUrl {
    fun postRecommendUrlListener(url: String, view: YouTubePlayerView)
}