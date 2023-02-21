package com.example.todayfeeling.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todayfeeling.R
import com.example.todayfeeling.data.EmotionDetail
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class BoardAdapter(private val itemList: ArrayList<EmotionDetail>): RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.txt_recycler_emotion)
        val youtube = itemView.findViewById<com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView>(R.id.recycler_player)
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
        holder.title.text = itemList[holder.bindingAdapterPosition].date.substring(16, 25)
        Log.e("URL", itemList[holder.bindingAdapterPosition].song)
        holder.youtube.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.cueVideo(itemList[holder.bindingAdapterPosition].song, 0f)
            }
        })
    }
}