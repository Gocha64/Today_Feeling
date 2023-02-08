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
        sharedPreferences.edit().remove("id")
        sharedPreferences.edit().remove("pw")
        sharedPreferences.edit().remove("session")
        val id = sharedPreferences.getString("id", "")
        val pw = sharedPreferences.getString("pw", "")
        val session = sharedPreferences.getString("session", "")

        if (id == "" && pw == "" && session == "") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}