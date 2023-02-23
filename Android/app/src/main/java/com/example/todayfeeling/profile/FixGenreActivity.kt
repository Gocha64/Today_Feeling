package com.example.todayfeeling.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.R
import com.example.todayfeeling.api.PostModifyGenre
import com.example.todayfeeling.databinding.ActivityFixGenreBinding
import com.example.todayfeeling.listener.AlertUserModifyGenre

class FixGenreActivity : AppCompatActivity(), AlertUserModifyGenre {
    private var mBinding: ActivityFixGenreBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityFixGenreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences("user", 0)
        var anger = sharedPreference.getString("anger","").toString()
        var fear = sharedPreference.getString("fear","").toString()
        var happy = sharedPreference.getString("happiness","").toString()
        var sad = sharedPreference.getString("sadness","").toString()
        var surprise = sharedPreference.getString("surprise","").toString()

        binding.txtFixAnger.text = changeStrToArr(anger.toCharArray().toTypedArray())
        binding.txtFixFear.text = changeStrToArr(fear.toCharArray().toTypedArray())
        binding.txtFixHappy.text = changeStrToArr(happy.toCharArray().toTypedArray())
        binding.txtFixSad.text = changeStrToArr(sad.toCharArray().toTypedArray())
        binding.txtFixSurprise.text = changeStrToArr(surprise.toCharArray().toTypedArray())

        binding.btnFixAnger.setOnClickListener {
            showAlertDialog(1, binding.txtFixAnger.text.toString(), anger, fear, happy, sad, surprise)
        }

        binding.btnFixFear.setOnClickListener {
            showAlertDialog(2, binding.txtFixFear.text.toString(), anger, fear, happy, sad, surprise)
        }

        binding.btnFixHappy.setOnClickListener {
            showAlertDialog(3, binding.txtFixHappy.text.toString(), anger, fear, happy, sad, surprise)
        }

        binding.btnFixSad.setOnClickListener {
            showAlertDialog(4, binding.txtFixSad.text.toString(), anger, fear, happy, sad, surprise)
        }

        binding.btnFixSurprise.setOnClickListener {
            showAlertDialog(5, binding.txtFixSurprise.text.toString(), anger, fear, happy, sad, surprise)
        }

        binding.btnFixedCheck.setOnClickListener {
            finish()
        }
    }

    private fun changeStrToArr(arr: Array<Char>): String {
        val emotion = arrayOf("클래식","재즈","팝","트로트","아이돌음악","락","발라드","게임음악","힙합","기타")
        var str =" "

        for (i: Int in 0..9) {
            if (arr[i].toString() == "1") {
                str = str.plus("${emotion[i]}, ")
            }
        }
        str = str.substring(0, str.length-2)
        return str
    }

    private fun showAlertDialog(
        id: Int,
        str: String,
        anger: String,
        fear: String,
        happy: String,
        sad: String,
        surprise: String
    ) {
        val dialogView = layoutInflater.inflate(R.layout.fix_genre_dialog, null)
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        val button = dialogView.findViewById<Button>(R.id.btn_fix_genre)

        when (id) {
            1 -> {
                dialogView.findViewById<TextView>(R.id.txt_alert_title).text = "화남"
                checkCb(str, dialogView)
            }
            2 -> {
                dialogView.findViewById<TextView>(R.id.txt_alert_title).text = "공포"
                checkCb(str, dialogView)
            }
            3 -> {
                dialogView.findViewById<TextView>(R.id.txt_alert_title).text = "행복"
                checkCb(str, dialogView)
            }
            4 -> {
                dialogView.findViewById<TextView>(R.id.txt_alert_title).text = "슬픔"
                checkCb(str, dialogView)
            }
            5 -> {
                dialogView.findViewById<TextView>(R.id.txt_alert_title).text = "놀람"
                checkCb(str, dialogView)
            }
        }

        button.setOnClickListener {
            alertDialog.dismiss()
            val emotion = fixGenre(dialogView)
            when (id) {
                1 -> {
                    PostModifyGenre(this,this).modifyGenre(String(emotion.toCharArray()), fear, happy, sad, surprise)
                }
                2 -> {
                    PostModifyGenre(this,this).modifyGenre(anger, String(emotion.toCharArray()), happy, sad, surprise)
                }
                3 -> {
                    PostModifyGenre(this,this).modifyGenre(anger, fear, String(emotion.toCharArray()), sad, surprise)
                }
                4 -> {
                    PostModifyGenre(this,this).modifyGenre(anger, fear, happy, String(emotion.toCharArray()), surprise)
                }
                5 -> {
                    PostModifyGenre(this,this).modifyGenre(anger, fear, happy, sad, String(emotion.toCharArray()))
                }
            }
        }

        alertDialog.show()
    }

    fun checkCb(str: String, dialogView: View) {
        var arr = str.split(",")
        for (emot: String in arr) {
            if (emot.contains("클래식")){
                dialogView.findViewById<CheckBox>(R.id.cb1).isChecked = true;
            } else if (emot.contains("재즈")) {
                dialogView.findViewById<CheckBox>(R.id.cb2).isChecked = true;
            } else if (emot.contains("팝")) {
                dialogView.findViewById<CheckBox>(R.id.cb3).isChecked = true;
            } else if (emot.contains("재즈")) {
                dialogView.findViewById<CheckBox>(R.id.cb4).isChecked = true;
            } else if (emot.contains("아이돌음악")) {
                dialogView.findViewById<CheckBox>(R.id.cb5).isChecked = true;
            } else if (emot.contains("락")) {
                dialogView.findViewById<CheckBox>(R.id.cb6).isChecked = true;
            } else if (emot.contains("발라드")) {
                dialogView.findViewById<CheckBox>(R.id.cb7).isChecked = true;
            } else if (emot.contains("게임음악")) {
                dialogView.findViewById<CheckBox>(R.id.cb8).isChecked = true;
            } else if (emot.contains("힙합")) {
                dialogView.findViewById<CheckBox>(R.id.cb9).isChecked = true;
            } else if (emot.contains("기타")) {
                dialogView.findViewById<CheckBox>(R.id.cb10).isChecked = true;
            }
        }
    }

    private fun fixGenre(dialogView: View): Array<Char> {
        var emotion = arrayOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0')

        val cb1 = dialogView.findViewById<CheckBox>(R.id.cb1)
        val cb2 = dialogView.findViewById<CheckBox>(R.id.cb2)
        val cb3 = dialogView.findViewById<CheckBox>(R.id.cb3)
        val cb4 = dialogView.findViewById<CheckBox>(R.id.cb4)
        val cb5 = dialogView.findViewById<CheckBox>(R.id.cb5)
        val cb6 = dialogView.findViewById<CheckBox>(R.id.cb6)
        val cb7 = dialogView.findViewById<CheckBox>(R.id.cb7)
        val cb8 = dialogView.findViewById<CheckBox>(R.id.cb8)
        val cb9 = dialogView.findViewById<CheckBox>(R.id.cb9)
        val cb10 = dialogView.findViewById<CheckBox>(R.id.cb10)

        emotion[0] = if (cb1.isChecked) '1' else '0'
        emotion[1] = if (cb2.isChecked) '1' else '0'
        emotion[2] = if (cb3.isChecked) '1' else '0'
        emotion[3] = if (cb4.isChecked) '1' else '0'
        emotion[4] = if (cb5.isChecked) '1' else '0'
        emotion[5] = if (cb6.isChecked) '1' else '0'
        emotion[6] = if (cb7.isChecked) '1' else '0'
        emotion[7] = if (cb8.isChecked) '1' else '0'
        emotion[8] = if (cb9.isChecked) '1' else '0'
        emotion[9] = if (cb10.isChecked) '1' else '0'

        return emotion
    }

    override fun alertUserModifyGenre() {
        val intent = intent
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }
}