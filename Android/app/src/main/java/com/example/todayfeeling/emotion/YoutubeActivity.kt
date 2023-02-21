package com.example.todayfeeling.emotion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todayfeeling.api.GetSongRandom
import com.example.todayfeeling.api.PostRecommend
import com.example.todayfeeling.data.EmotionRecommend
import com.example.todayfeeling.databinding.ActivityYoutubeBinding
import com.example.todayfeeling.listener.PostRecommendUrl
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class YoutubeActivity : AppCompatActivity(), PostRecommendUrl {
    private var mBinding: ActivityYoutubeBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        val emotion = intent.getIntExtra("emotion", 0)
        Log.e("emotion", emotion.toString())
        PostRecommend(this).recommendSong(EmotionRecommend(emotion))

        when (emotion) {
            1 -> {
                binding.txtRecommendEmotion.text = "화남"
            }
            2 -> {
                binding.txtRecommendEmotion.text = "공포"
            }
            3 -> {
                binding.txtRecommendEmotion.text = "행복"
            }
            4 -> {
                binding.txtRecommendEmotion.text = "슬픔"
            }
            5 -> {
                binding.txtRecommendEmotion.text = "놀람"
            }
        }
    }

    private fun start() {
        val intent = Intent(this, EmotionClassificationActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        start()
    }

    override fun postRecommendUrlListener(url: String) {
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(url, 0f)
            }

            override fun onStateChange(
                youTubePlayer: YouTubePlayer,
                state: PlayerConstants.PlayerState
            ) {
                super.onStateChange(youTubePlayer, state)
                if (state.toString() == "ENDED") {
                    start()
                }
            }
        })
    }
}