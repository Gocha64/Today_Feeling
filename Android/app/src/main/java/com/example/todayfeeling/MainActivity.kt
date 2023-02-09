package com.example.todayfeeling

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.todayfeeling.databinding.ActivityMainBinding
import com.example.todayfeeling.main.MainFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.body_container,MainFragment(), "main").commit()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.main_page -> {
                    changeFragment(MainFragment(), "main")
                }
                else -> {
                    changeFragment(MainFragment(), "main")
                }
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment, name : String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.body_container, fragment, name)
            .addToBackStack(null)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }

    fun updateBottomMenu(navigation: BottomNavigationView) {
        val tag1: Fragment? = supportFragmentManager.findFragmentByTag("main")

        if(tag1 != null && tag1.isVisible) navigation.menu.findItem(R.id.main).isChecked = true
    }

    override fun onBackPressed() {
        val bnv = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        updateBottomMenu(bnv)
    }
}