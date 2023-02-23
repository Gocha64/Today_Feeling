package com.example.todayfeeling.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todayfeeling.R
import com.example.todayfeeling.data.EmotionDetail
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class BoardAdapter(private val itemList: ArrayList<EmotionDetail>, val context: Context): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val youtube = itemView.findViewById<LinearLayout>(R.id.recycler_player)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.emotion_item_list, parent, false)
        return BoardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val lp = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        holder.youtube.removeAllViews()
        val youTubePlayerView = YouTubePlayerView(context)
        holder.youtube.addView(youTubePlayerView, lp)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(itemList[holder.bindingAdapterPosition].song, 0f)
            }
        })
    }
}