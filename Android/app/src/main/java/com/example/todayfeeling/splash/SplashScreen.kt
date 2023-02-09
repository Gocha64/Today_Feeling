package com.example.todayfeeling.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.sign.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user", 0)
        val id = sharedPreferences.getString("id", "")
        val pw = sharedPreferences.getString("pw", "")
        val session = sharedPreferences.getString("session", "")
        Log.d("test","$id")

        if (id == "" && pw == "" && session == "") {
            //세션 아이디 수정하면 LoginActivity로 변경
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}