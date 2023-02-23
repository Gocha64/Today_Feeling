package com.example.todayfeeling.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import com.example.todayfeeling.R
import com.example.todayfeeling.api.PostRegister
import com.example.todayfeeling.databinding.ActivitySignUpBinding
import com.example.todayfeeling.emotion.EmotionClassificationActivity

class SignUpActivity : AppCompatActivity() {
    private var mBinding: ActivitySignUpBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val items = resources.getStringArray(R.array.sex_array)

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        binding.editSignupSex.adapter = myAdapter
        var sex: String = "1"

        binding.editSignupSex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //아이템이 클릭 되면 맨 위부터 position 0번부터 순서대로 동작하게 됩니다.
                when(position) {
                    0   ->  {
                        sex = "1"
                    }
                    1   ->  {
                        sex = "2"
                    }
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        binding.btnNextRegister.setOnClickListener {
            val intent = Intent(this, SignUpEmotionActivity::class.java)
            intent.putExtra("name", binding.editSignupName.text.toString())
            intent.putExtra("id", binding.editSignupId.text.toString())
            intent.putExtra("pw", binding.editSignupPw.text.toString())
            intent.putExtra("email", binding.editSignupEmail.text.toString())
            intent.putExtra("sex", sex)
            startActivity(intent)
        }

        binding.btnBackLogin.setOnClickListener {
            onBackPressed()
        }
    }

    private fun start() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        start()
    }
}