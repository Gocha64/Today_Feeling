package com.example.todayfeeling.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todayfeeling.databinding.FragmentProfileBinding
import com.example.todayfeeling.sign.LoginActivity

class ProfileFragment : Fragment() {

    private var mBinding: FragmentProfileBinding? = null

    private val binding get() = mBinding!!

    private lateinit var anger:Array<Char>

    override fun onResume() {
        super.onResume()
        val sharedPreference = requireActivity().getSharedPreferences("user", 0)
        binding.txtNameMain.text = sharedPreference.getString("name", "").toString()
        binding.txtName.text = sharedPreference.getString("name", "").toString()
        binding.txtId.text = sharedPreference.getString("id","").toString()
        binding.txtEmail.text = sharedPreference.getString("email","").toString()
        binding.txtSex.text = if (sharedPreference.getString("sex","") == "1") "남자" else "여자"

        binding.txtAnger.text = changeStrToArr(sharedPreference.getString("anger","").toString().toCharArray().toTypedArray())
        binding.txtFear.text = changeStrToArr(sharedPreference.getString("fear","").toString().toCharArray().toTypedArray())
        binding.txtHappy.text = changeStrToArr(sharedPreference.getString("happiness","").toString().toCharArray().toTypedArray())
        binding.txtSad.text = changeStrToArr(sharedPreference.getString("sadness","").toString().toCharArray().toTypedArray())
        binding.txtSurprise.text = changeStrToArr(sharedPreference.getString("surprise","").toString().toCharArray().toTypedArray())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false)

        val sharedPreference = requireActivity().getSharedPreferences("user", 0)

        binding.btnLogout.setOnClickListener {
            sharedPreference.edit().remove("id")
            sharedPreference.edit().remove("pw")
            sharedPreference.edit().remove("sex")
            sharedPreference.edit().remove("email")
            sharedPreference.edit().remove("anger")
            sharedPreference.edit().remove("name")
            sharedPreference.edit().remove("happiness")
            sharedPreference.edit().remove("fear")
            sharedPreference.edit().remove("sadness")
            sharedPreference.edit().remove("surprise")
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

        binding.btnFixGenre.setOnClickListener {
            val intent = Intent(activity, FixGenreActivity::class.java)
            startActivity(intent)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun changeStrToArr(array: Array<Char>): String {
        val emotion = arrayOf("클래식","재즈","팝","트로트","아이돌음악","락","발라드","게임음악","힙합","기타")
        var str =" "

        for (i: Int in 0..9) {
            if (array[i].toString() == "1") {
                str = str.plus("${emotion[i]}, ")
            }
        }
        return str
    }
}