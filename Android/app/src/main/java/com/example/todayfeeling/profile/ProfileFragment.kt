package com.example.todayfeeling.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todayfeeling.databinding.FragmentProfileBinding
import com.example.todayfeeling.sign.LoginActivity

class ProfileFragment : Fragment() {

    private var mBinding: FragmentProfileBinding? = null

    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPreference = requireActivity().getSharedPreferences("user", 0)
        sharedPreference.getString("id", "")
        sharedPreference.getString("sex", "")

        binding.btnLogout.setOnClickListener {
            sharedPreference.edit().remove("id")
            sharedPreference.edit().remove("pw")
            sharedPreference.edit().remove("sex")
            sharedPreference.edit().remove("session")
            sharedPreference.edit().apply()
            activity?.let{
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.btnFixUser.setOnClickListener {
            val intent = Intent(activity, FixUserActivity::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}