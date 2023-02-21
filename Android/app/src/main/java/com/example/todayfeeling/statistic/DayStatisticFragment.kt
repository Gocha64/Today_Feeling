package com.example.todayfeeling.statistic

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todayfeeling.adapter.BoardAdapter
import com.example.todayfeeling.api.GetEmotionDetailStatistic
import com.example.todayfeeling.api.GetEmotionStatistic
import com.example.todayfeeling.data.Emotion
import com.example.todayfeeling.data.EmotionData
import com.example.todayfeeling.data.EmotionDetail
import com.example.todayfeeling.data.EmotionDetailData
import com.example.todayfeeling.databinding.FragmentDayStatisticBinding
import com.example.todayfeeling.listener.GetStatistic
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import java.time.LocalDate
import java.time.ZoneId
import com.github.mikephil.charting.components.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class DayStatisticFragment : Fragment(), GetStatistic {

    private val anger = arrayListOf<EmotionDetail>()
    private val fear = arrayListOf<EmotionDetail>()
    private val happy = arrayListOf<EmotionDetail>()
    private val sad = arrayListOf<EmotionDetail>()
    private val surprise = arrayListOf<EmotionDetail>()
    private var emotion = arrayListOf<EmotionDetail>()
    inner class MyXaxisFormatter : ValueFormatter() {
        private val emotion = arrayOf("화남", "공포", "행복", "슬픔", "놀람")
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return emotion.getOrNull(value.toInt()) ?: value.toString()
        }
    }

    private var mBinding: FragmentDayStatisticBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentDayStatisticBinding.inflate(inflater, container, false)

        val boardAdapter=BoardAdapter(emotion)
        binding.recyclerView.adapter = boardAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        var dailyDate = LocalDate.now()
        var date = ("%02d년 %02d월 %02d일").format(dailyDate.year, dailyDate.monthValue,dailyDate.dayOfMonth)

        binding.txtDay.text = date
        var day = dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
        GetEmotionStatistic(this).dayEmotionStatistic(day.substring(0 until 10))
        GetEmotionDetailStatistic(this).dayEmotionDetailStatistic(day.substring(0 until 10))

        binding.btnNextDay.setOnClickListener {
            dailyDate = dailyDate.plusDays(1)
            if(dailyDate.dayOfMonth>dailyDate.lengthOfMonth()){
                dailyDate.plusMonths(1)
                dailyDate.withDayOfMonth(1)
                dailyDate.plusYears(1)
            }
            date = ("%02d년 %02d월 %02d일").format(dailyDate.year, dailyDate.monthValue,dailyDate.dayOfMonth)
            day = dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
            GetEmotionStatistic(this).dayEmotionStatistic(day.substring(0 until 10))
            GetEmotionDetailStatistic(this).dayEmotionDetailStatistic(day.substring(0 until 10))
            Log.d("day", date)
            binding.txtDay.text = date
            binding.txtEmotion.visibility = View.INVISIBLE
            emotion.clear()
            boardAdapter.notifyDataSetChanged()
        }

        binding.btnPreviousDay.setOnClickListener {
            dailyDate = dailyDate.minusDays(1)
            if(dailyDate.dayOfMonth == 0){
                dailyDate.minusMonths(1)
                dailyDate.withDayOfMonth(dailyDate.monthValue)
                dailyDate.minusYears(-1)
            }
            date = ("%02d년 %02d월 %02d일").format(dailyDate.year, dailyDate.monthValue,dailyDate.dayOfMonth)
            day = dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
            GetEmotionStatistic(this).dayEmotionStatistic(day.substring(0 until 10))
            GetEmotionDetailStatistic(this).dayEmotionDetailStatistic(day.substring(0 until 10))
            binding.txtDay.text = date
            binding.txtEmotion.visibility = View.INVISIBLE
            emotion.clear()
            boardAdapter.notifyDataSetChanged()
        }
        initBarChart(binding.chartStatistic)

        binding.chartStatistic.setOnChartValueSelectedListener(object: OnChartValueSelectedListener{
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val xLabel = e!!.x.let {
                    binding.chartStatistic.xAxis.valueFormatter.getAxisLabel(it, binding.chartStatistic.xAxis)
                }
                emotion.clear()
                binding.txtEmotion.text = xLabel
                binding.txtEmotion.visibility = View.VISIBLE
                when (xLabel) {
                    "화남" -> {
                        emotion.addAll(anger)
                    }
                    "공포" -> {
                        emotion.addAll(fear)
                    }
                    "행복" -> {
                        emotion.addAll(happy)
                    }
                    "슬픔" -> {
                        emotion.addAll(sad)
                    }
                    "놀람" -> {
                        emotion.addAll(surprise)
                    }
                }
                boardAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected() {
                binding.txtEmotion.visibility = View.INVISIBLE
                emotion.clear()
                boardAdapter.notifyDataSetChanged()
            }

        })
        return binding.root
    }

    // 바 차트 설정
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
        setData(binding.chartStatistic, data.result)
    }

    override fun getDayDetailStatistic(data: ArrayList<EmotionDetail>) {
        Log.e("detailData", data.toString())

        for (i in data) {
            when (i.emotion) {
                1 -> {
                    anger.add(i)
                }
                2 -> {
                    fear.add(i)
                }
                3 -> {
                    happy.add(i)
                }
                4 -> {
                    sad.add(i)
                }
                5 -> {
                    surprise.add(i)
                }
            }
        }

        Log.e("anger", anger.toString())
        Log.e("fear", fear.toString())
        Log.e("happy", happy.toString())
        Log.e("sad", sad.toString())
        Log.e("surprise", surprise.toString())
    }


}