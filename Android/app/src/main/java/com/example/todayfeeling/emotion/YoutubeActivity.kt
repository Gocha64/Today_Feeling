package com.example.todayfeeling.emotion

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.api.PostRecommend
import com.example.todayfeeling.data.EmotionRecommend
import com.example.todayfeeling.databinding.ActivityYoutubeBinding
import com.example.todayfeeling.listener.PostRecommendUrl
import com.example.todayfeeling.main.MainFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class YoutubeActivity : AppCompatActivity(), PostRecommendUrl {
    private var mBinding: ActivityYoutubeBinding? = null
    private val binding get() = mBinding!!
    private val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityYoutubeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent: Intent = intent
        val emotion = intent.getIntExtra("emotion", 0)
        val youTubePlayerView = YouTubePlayerView(this)
        binding.youtubePlayerView.addView(youTubePlayerView, lp)
        Log.e("emotion", emotion.toString())
        PostRecommend(this, youTubePlayerView).recommendSong(EmotionRecommend(emotion))

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

        binding.btnExitYoutube.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
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

    override fun postRecommendUrlListener(url: String,view: YouTubePlayerView) {
        lifecycle.addObserver(view)
        binding.youtubePlayerView.removeAllViews()
        val youTubePlayerView = YouTubePlayerView(this)
        binding.youtubePlayerView.addView(youTubePlayerView, lp)
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object :
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