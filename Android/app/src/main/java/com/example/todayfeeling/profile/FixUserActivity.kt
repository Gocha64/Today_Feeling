package com.example.todayfeeling.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.R
import com.example.todayfeeling.api.PostModifyUser
import com.example.todayfeeling.databinding.ActivityFixUserBinding
import com.example.todayfeeling.listener.AlertUserModify

class FixUserActivity : AppCompatActivity(), AlertUserModify {
    private var mBinding:ActivityFixUserBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFixUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("user", 0)

        binding.txtFixId.text = sharedPreference.getString("id", "").toString()
        val pw = sharedPreference.getString("pw", "").toString()
        binding.txtFixPw.text = "****"
        binding.txtFixSex.text = sharedPreference.getString("sex","").toString()
        binding.txtFixName.text = sharedPreference.getString("name","").toString()
        binding.txtFixEmail.text = sharedPreference.getString("email","").toString()

        binding.btnFixName.setOnClickListener {
            showAlertDialog(0, pw)
        }

        binding.btnFixId.setOnClickListener {
            showAlertDialog(1, pw)
        }

        binding.btnFixPw.setOnClickListener {
            showAlertDialog(2, pw)

        }

        binding.btnFixEmail.setOnClickListener {
            showAlertDialog(3, pw)
        }

        binding.btnFixSex.setOnClickListener {
            showAlertDialog(4, pw)
        }

        binding.btnFixedCheck.setOnClickListener {
            finish()
        }
    }
    private fun showAlertDialog(id:Int, pw:String) {
        val dialogView = layoutInflater.inflate(R.layout.fix_user_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val button = dialogView.findViewById<Button>(R.id.btn_fix)
        val edit = dialogView.findViewById<EditText>(R.id.edit_fix)?.text

        button.setOnClickListener {
            alertDialog.dismiss()
            when (id) {
                0 -> {
                    PostModifyUser(this, this).modifyUser(
                        binding.txtFixId.text.toString(),
                        pw,
                        edit.toString(),
                        binding.txtFixSex.text.toString(),
                        binding.txtFixEmail.text.toString()
                    )
                }
                1 -> {
                    PostModifyUser(this, this).modifyUser(
                        edit.toString(),
                        pw,
                        binding.txtFixName.text.toString(),
                        binding.txtFixSex.text.toString(),
                        binding.txtFixEmail.text.toString()
                    )
                }
                2 -> {
                    PostModifyUser(this,this).modifyUser(
                        binding.txtFixId.text.toString(),
                        edit.toString(),
                        binding.txtFixName.text.toString(),
                        binding.txtFixSex.text.toString(),
                        binding.txtFixEmail.text.toString()
                    )
                }
                3 -> {
                    PostModifyUser(this,this).modifyUser(
                        binding.txtFixId.text.toString(),
                        pw,
                        binding.txtFixName.text.toString(),
                        binding.txtFixSex.text.toString(),
                        edit.toString()
                    )
                }
                else -> {
                    PostModifyUser(this,this).modifyUser(
                        binding.txtFixId.text.toString(),
                        pw,
                        binding.txtFixName.text.toString(),
                        edit.toString(),
                        binding.txtFixEmail.text.toString()
                    )
                }
            }
        }

        when (id) {
            0 -> {
                dialogView.findViewById<TextView>(R.id.txt_fix).text = "이름 변경"
            }
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

    override fun alertUserModify() {
        val intent = intent
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}