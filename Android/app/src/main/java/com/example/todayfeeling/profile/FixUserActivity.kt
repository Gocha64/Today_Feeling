package com.example.todayfeeling.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.R
import com.example.todayfeeling.databinding.ActivityFixUserBinding

class FixUserActivity : AppCompatActivity() {
    private var mBinding:ActivityFixUserBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFixUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFixId.setOnClickListener {
            showAlertDialog(1)
        }

        binding.btnFixPw.setOnClickListener {
            showAlertDialog(2)

        }

        binding.btnFixEmail.setOnClickListener {
            showAlertDialog(3)
        }

        binding.btnFixSex.setOnClickListener {
            showAlertDialog(4)
        }

        binding.btnFixedCheck.setOnClickListener {
            //서버 전송 및 sharedPreference 값 수정
        }
    }
    fun showAlertDialog(id:Int) {
        val dialogView = layoutInflater.inflate(R.layout.fix_user_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val button = dialogView.findViewById<Button>(R.id.btn_fix)
        val edit = dialogView.findViewById<EditText>(R.id.edit_fix)?.text

        button.setOnClickListener {
            alertDialog.dismiss()
            when (id) {
                1 -> {
                    binding.txtFixedId.text = edit
                }
                2 -> {
                    binding.txtFixedPw.text = edit
                }
                3 -> {
                    binding.txtFixEmail.text = edit
                }
                else -> {
                    binding.txtFixedSex.text = edit
                }
            }
        }

        when (id) {
            1 -> {
                Log.d("test","아이디 변경")
                dialogView.findViewById<TextView>(R.id.txt_fix).text = "아이디 변경"
            }
            2 -> {
                dialogView.findViewById<TextView>(R.id.txt_fix).text = "비밀번호 변경"
            }
            3 -> {
                dialogView.findViewById<TextView>(R.id.txt_fix).text = "이메일 변경"
            }
            else -> {
                dialogView.findViewById<TextView>(R.id.txt_fix).text = "성별 변경"
            }
        }

        alertDialog.show()
    }
}