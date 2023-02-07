package com.example.todayfeeling.sign

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.data.LoginData
import com.example.todayfeeling.databinding.ActivityLoginBinding
import com.example.todayfeeling.network.RetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private var mBinding: ActivityLoginBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener {
            // 실제 로그인시 사용
            startLogin(binding.editId.text.toString(),binding.editPw.text.toString(), this)
        }
    }
    // 로그인을 위한 서버 통신
    fun startLogin(id: String, pw: String, context: Context) {
        val call = RetrofitImpl.service.postSignIn(LoginData(id,pw))
        var sessionId = ""
        val dialog = AlertDialog.Builder(context)
            .setTitle("로그인 시도중")
            .setMessage("잠시만 기다려주세요.")
            .create()

        dialog.show()

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    if (response.body().toString() == "success") {
                        sessionId = response.headers().toString()
                        val sharedPreference = getSharedPreferences("user", 0)
                        val editor = sharedPreference.edit()
                        editor.putString("id", id)
                        editor.putString("pw", pw)
                        editor.putString("session", sessionId)
                        editor.apply()
                        dialog.dismiss()
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }
                    else {
                        dialog.dismiss()
                        Log.d("test","${response.body().toString()}")
                        val dialog = AlertDialog.Builder(context)
                            .setTitle("로그인 실패")
                            .setMessage("다시 시도해주세요.")
                            .create()

                        dialog.show()
                        //로그인 실패 알리기
                    }
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                dialog.dismiss()
                val dialog = AlertDialog.Builder(context)
                    .setTitle("서버 오류")
                    .setMessage("잠시만 기다려주세요.")
                    .create()

                dialog.show()
                // 서버 에러 알림창 띄우기
            }

        })
    }
}