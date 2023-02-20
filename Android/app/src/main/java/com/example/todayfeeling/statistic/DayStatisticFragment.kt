package com.example.todayfeeling.statistic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todayfeeling.api.GetEmotionStatistic
import com.example.todayfeeling.databinding.FragmentDayStatisticBinding
import java.time.LocalDate
import java.time.ZoneId

class DayStatisticFragment : Fragment() {

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

        var dailyDate = LocalDate.now()
        var date = ("%02d년 %02d월 %02d일").format(dailyDate.year, dailyDate.monthValue,dailyDate.dayOfMonth)

        binding.txtDay.text = date
        var day = dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
        GetEmotionStatistic().dayEmotionStatistic(day.substring(0 until 10))

        binding.btnNextDay.setOnClickListener {
            dailyDate = dailyDate.plusDays(1)
            if(dailyDate.dayOfMonth>dailyDate.lengthOfMonth()){
                dailyDate.plusMonths(1)
                dailyDate.withDayOfMonth(1)
                dailyDate.plusYears(1)
            }
            date = ("%02d년 %02d월 %02d일").format(dailyDate.year, dailyDate.monthValue,dailyDate.dayOfMonth)
            day = dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli().toString()
            GetEmotionStatistic().dayEmotionStatistic(day.substring(0 until 10))
            Log.d("day", date)
            binding.txtDay.text = date
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
            GetEmotionStatistic().dayEmotionStatistic(day.substring(0 until 10))
            binding.txtDay.text = date
        }

        return binding.root
    }
}