package com.example.todayfeeling.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.todayfeeling.R
import com.example.todayfeeling.api.GetEmotionStatistic
import com.example.todayfeeling.api.GetSongRandom
import com.example.todayfeeling.api.PostRecommend
import com.example.todayfeeling.data.Emotion
import com.example.todayfeeling.data.EmotionData
import com.example.todayfeeling.data.EmotionDetail
import com.example.todayfeeling.data.EmotionRecommend
import com.example.todayfeeling.databinding.FragmentMainBinding
import com.example.todayfeeling.emotion.EmotionClassificationActivity
import com.example.todayfeeling.listener.GetStatistic
import com.example.todayfeeling.listener.PostRecommendUrl
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.time.LocalDate
import java.time.ZoneId


class MainFragment : Fragment(), PostRecommendUrl, GetStatistic {
    private var mBinding: FragmentMainBinding? = null

    private val binding get() = mBinding!!

    private var dailyDate = LocalDate.now()
    private var day = dailyDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant().epochSecond.plus(32400).toString()

    private val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    inner class MyXaxisFormatter : ValueFormatter() {
        private val emotion = arrayOf("화남", "공포", "행복", "슬픔", "놀람")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return emotion.getOrNull(value.toInt()) ?: value.toString()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GetEmotionStatistic(this).dayEmotionStatistic(day.substring(0 until 10))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)

        val items = resources.getStringArray(R.array.emotion_array)

        val myAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)

        binding.spinner.adapter = myAdapter

        val youTubePlayerView = YouTubePlayerView(requireContext())
        binding.youtubePlayer.addView(youTubePlayerView, lp)

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0   ->  {
                        recommendUrl(0,youTubePlayerView)
                    }
                    1   ->  {
                        recommendUrl(1,youTubePlayerView)
                    }
                    2   ->  {
                        recommendUrl(2,youTubePlayerView)
                    }
                    3   ->  {
                        recommendUrl(3,youTubePlayerView)
                    }
                    4   ->  {
                        recommendUrl(4,youTubePlayerView)
                    }
                    5   ->  {
                        recommendUrl(5,youTubePlayerView)
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnStartCamera.setOnClickListener {
            val intent = Intent(activity, EmotionClassificationActivity::class.java)
            startActivity(intent)
        }

        initBarChart(binding.chartMain)
        // Inflate the layout for this fragment
        return binding.root
    }

    fun recommendUrl(emotion: Int,view: YouTubePlayerView) {
        if (emotion == 0) {
            GetSongRandom(this, view).getSongRandom()
        } else {
            Log.e("emotion", emotion.toString())
            PostRecommend(this,view).recommendSong(EmotionRecommend(emotion))
        }
    }

    override fun postRecommendUrlListener(url: String, view: YouTubePlayerView) {
        Log.e("testing", url)
        binding.youtubePlayer.removeAllViews()
        val youTubePlayerView = YouTubePlayerView(requireContext())
        binding.youtubePlayer.addView(youTubePlayerView, lp)
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.cueVideo(url, 0f)
            }
        })
        GetEmotionStatistic(this).dayEmotionStatistic(day.substring(0 until 10))
    }

    private fun initBarChart(barChart: BarChart) {
        // 차트 회색 배경 설정 (default = false)
        barChart.setDrawGridBackground(false)
        // 막대 그림자 설정 (default = false)
        barChart.setDrawBarShadow(false)
        // 차트 테두리 설정 (default = false)
        barChart.setDrawBorders(false)

        val description = Description()
        // 오른쪽 하단 모서리 설명 레이블 텍스트 표시 (default = false)
        description.isEnabled = false
        barChart.description = description
        barChart.setTouchEnabled(false)

        // X, Y 바의 애니메이션 효과
        barChart.animateY(1000)
        barChart.animateX(1000)

        // 바텀 좌표 값
        val xAxis: XAxis = barChart.xAxis
        // x축 위치 설정
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        // 그리드 선 수평 거리 설정
        xAxis.granularity = 1f
        // x축 텍스트 컬러 설정
        xAxis.textColor = Color.RED
        // x축 선 설정 (default = true)
        xAxis.setDrawAxisLine(false)
        // 격자선 설정 (default = true)
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = MyXaxisFormatter()

        val leftAxis: YAxis = barChart.axisLeft
        // 좌측 선 설정 (default = true)
        leftAxis.setDrawAxisLine(false)
        // 좌측 텍스트 컬러 설정
        leftAxis.textColor = Color.BLUE

        val rightAxis: YAxis = barChart.axisRight
        // 우측 선 설정 (default = true)
        rightAxis.setDrawAxisLine(false)
        // 우측 텍스트 컬러 설정
        rightAxis.textColor = Color.GREEN

        // 바차트의 타이틀
        val legend: Legend = barChart.legend
        // 범례 모양 설정 (default = 정사각형)
        legend.form = Legend.LegendForm.LINE
        // 타이틀 텍스트 사이즈 설정
        legend.textSize = 20f
        // 타이틀 텍스트 컬러 설정
        legend.textColor = Color.BLACK
        // 범례 위치 설정
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        // 범례 방향 설정
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        // 차트 내부 범례 위치하게 함 (default = false)
        legend.setDrawInside(false)
    }

    private fun setData(barChart: BarChart, data: Emotion) {

        barChart.invalidate()
        // Zoom In / Out 가능 여부 설정
        barChart.setScaleEnabled(false)

        val valueList = ArrayList<BarEntry>()
        val title = "감정"

        valueList.add(BarEntry(0f, data.anger.toFloat()))
        valueList.add(BarEntry(1f, data.fear.toFloat()))
        valueList.add(BarEntry(2f, data.happy.toFloat()))
        valueList.add(BarEntry(3f, data.sad.toFloat()))
        valueList.add(BarEntry(4f, data.surprise.toFloat()))

        val barDataSet = BarDataSet(valueList, title)
        // 바 색상 설정 (ColorTemplate.LIBERTY_COLORS)
        barDataSet.setColors(
            Color.rgb(207, 248, 246), Color.rgb(148, 212, 212), Color.rgb(136, 180, 187),
            Color.rgb(118, 174, 175), Color.rgb(42, 109, 130))

        val dataSet:ArrayList<IBarDataSet> = ArrayList()
        dataSet.add(barDataSet)

        val data = BarData(dataSet)
        barChart.data = data

        // 차트 새로고침
        barChart.invalidate()
    }

    override fun getDayStatistic(data: EmotionData) {
        Log.e("dayStatistic", data.result.toString())
        setData(binding.chartMain, data.result)
    }

    override fun getDayDetailStatistic(data: ArrayList<EmotionDetail>) {
        TODO("Not yet implemented")
    }
}