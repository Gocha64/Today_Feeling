package com.example.todayfeeling.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.sign.LoginActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user", 0)
        val id = sharedPreferences.getString("id", "")
        val pw = sharedPreferences.getString("pw", "")

        if (id == "" && pw == "") {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}