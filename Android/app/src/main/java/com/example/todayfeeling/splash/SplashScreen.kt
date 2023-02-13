package com.example.todayfeeling.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.api.PostLogin
import com.example.todayfeeling.data.LoginData
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.data.UserData
import com.example.todayfeeling.network.RetrofitImpl
import com.example.todayfeeling.sign.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user", 0)
        val id = sharedPreferences.getString("id", "")
        val pw = sharedPreferences.getString("pw", "")
        Log.d("test","$id")

        if (id == "" && pw == "") {
            //세션 아이디 수정하면 LoginActivity로 변경
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        } else {
            if (id != null) {
                if (pw != null) {
//                    PostLogin().startLogin(id, pw, this)
                }
            }
        }
    }
}