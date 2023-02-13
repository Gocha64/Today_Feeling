package com.example.todayfeeling.sign

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todayfeeling.api.PostRegister
import com.example.todayfeeling.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private var mBinding: ActivitySignUpBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
//            PostRegister().signUpRegister(binding.editSignupId.text.toString(), binding.editSignupPw.text.toString(),
//                binding.editSignupName.text.toString(), binding.editSignupSex.text.toString(), this)
        }
    }
}