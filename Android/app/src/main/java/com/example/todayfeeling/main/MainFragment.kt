package com.example.todayfeeling.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.databinding.FragmentMainBinding
import com.example.todayfeeling.emotion.EmotionClassificationActivity
import com.example.todayfeeling.profile.FixUserActivity

class MainFragment : Fragment() {
    private var mBinding: FragmentMainBinding? = null

    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)

        binding.btnStartCamera.setOnClickListener {
            val intent = Intent(activity, EmotionClassificationActivity::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}