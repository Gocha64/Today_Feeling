package com.example.todayfeeling.sign

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import com.example.todayfeeling.R
import com.example.todayfeeling.api.PostRegister
import com.example.todayfeeling.databinding.ActivitySignUpEmotionBinding
import com.example.todayfeeling.emotion.EmotionClassificationActivity

class SignUpEmotionActivity : AppCompatActivity() {

    private var mBinding: ActivitySignUpEmotionBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpEmotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val pw = intent.getStringExtra("pw")
        val email = intent.getStringExtra("email")
        val sex = intent.getStringExtra("sex")

        var anger = arrayOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0')
        var fear = arrayOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0')
        var happy = arrayOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0')
        var sad = arrayOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0')
        var surprise = arrayOf('0', '0', '0', '0', '0', '0', '0', '0', '0', '0')

        val angerListener = CompoundButton.OnCheckedChangeListener { buttonView,
                                                                     isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.cb_anger1 -> {anger[0] = '1'}
                    R.id.cb_anger2 -> {anger[1] = '1'}
                    R.id.cb_anger3 -> {anger[2] = '1'}
                    R.id.cb_anger4 -> {anger[3] = '1'}
                    R.id.cb_anger5 -> {anger[4] = '1'}
                    R.id.cb_anger6 -> {anger[5] = '1'}
                    R.id.cb_anger7 -> {anger[6] = '1'}
                    R.id.cb_anger8 -> {anger[7] = '1'}
                    R.id.cb_anger9 -> {anger[8] = '1'}
                    R.id.cb_anger10 -> {anger[9] = '1'}
                }
            } else{
                when (buttonView.id) {
                    R.id.cb_anger1 -> {anger[0] = '0'}
                    R.id.cb_anger2 -> {anger[1] = '0'}
                    R.id.cb_anger3 -> {anger[2] = '0'}
                    R.id.cb_anger4 -> {anger[3] = '0'}
                    R.id.cb_anger5 -> {anger[4] = '0'}
                    R.id.cb_anger6 -> {anger[5] = '0'}
                    R.id.cb_anger7 -> {anger[6] = '0'}
                    R.id.cb_anger8 -> {anger[7] = '0'}
                    R.id.cb_anger9 -> {anger[8] = '0'}
                    R.id.cb_anger10 -> {anger[9] = '0'}
                }
            }
        }

        binding.cbAnger1.setOnCheckedChangeListener(angerListener)
        binding.cbAnger2.setOnCheckedChangeListener(angerListener)
        binding.cbAnger3.setOnCheckedChangeListener(angerListener)
        binding.cbAnger4.setOnCheckedChangeListener(angerListener)
        binding.cbAnger5.setOnCheckedChangeListener(angerListener)
        binding.cbAnger6.setOnCheckedChangeListener(angerListener)
        binding.cbAnger7.setOnCheckedChangeListener(angerListener)
        binding.cbAnger8.setOnCheckedChangeListener(angerListener)
        binding.cbAnger9.setOnCheckedChangeListener(angerListener)
        binding.cbAnger10.setOnCheckedChangeListener(angerListener)

        val fearListener = CompoundButton.OnCheckedChangeListener { buttonView,
                                                                    isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.cb_fear1 -> {fear[0] = '1'}
                    R.id.cb_fear2 -> {fear[1] = '1'}
                    R.id.cb_fear3 -> {fear[2] = '1'}
                    R.id.cb_fear4 -> {fear[3] = '1'}
                    R.id.cb_fear5 -> {fear[4] = '1'}
                    R.id.cb_fear6 -> {fear[5] = '1'}
                    R.id.cb_fear7 -> {fear[6] = '1'}
                    R.id.cb_fear8 -> {fear[7] = '1'}
                    R.id.cb_fear9 -> {fear[8] = '1'}
                    R.id.cb_fear10 -> {fear[9] = '1'}
                }
            } else{
                when (buttonView.id) {
                    R.id.cb_fear1 -> {fear[0] = '0'}
                    R.id.cb_fear2 -> {fear[1] = '0'}
                    R.id.cb_fear3 -> {fear[2] = '0'}
                    R.id.cb_fear4 -> {fear[3] = '0'}
                    R.id.cb_fear5 -> {fear[4] = '0'}
                    R.id.cb_fear6 -> {fear[5] = '0'}
                    R.id.cb_fear7 -> {fear[6] = '0'}
                    R.id.cb_fear8 -> {fear[7] = '0'}
                    R.id.cb_fear9 -> {fear[8] = '0'}
                    R.id.cb_fear10 -> {fear[9] = '0'}
                }
            }
        }

        binding.cbFear1.setOnCheckedChangeListener(fearListener)
        binding.cbFear2.setOnCheckedChangeListener(fearListener)
        binding.cbFear3.setOnCheckedChangeListener(fearListener)
        binding.cbFear4.setOnCheckedChangeListener(fearListener)
        binding.cbFear5.setOnCheckedChangeListener(fearListener)
        binding.cbFear6.setOnCheckedChangeListener(fearListener)
        binding.cbFear7.setOnCheckedChangeListener(fearListener)
        binding.cbFear8.setOnCheckedChangeListener(fearListener)
        binding.cbFear9.setOnCheckedChangeListener(fearListener)
        binding.cbFear10.setOnCheckedChangeListener(fearListener)

        val happyListener = CompoundButton.OnCheckedChangeListener { buttonView,
                                                                     isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.cb_happy1 -> {happy[0] = '1'}
                    R.id.cb_happy2 -> {happy[1] = '1'}
                    R.id.cb_happy3 -> {happy[2] = '1'}
                    R.id.cb_happy4 -> {happy[3] = '1'}
                    R.id.cb_happy5 -> {happy[4] = '1'}
                    R.id.cb_happy6 -> {happy[5] = '1'}
                    R.id.cb_happy7 -> {happy[6] = '1'}
                    R.id.cb_happy8 -> {happy[7] = '1'}
                    R.id.cb_happy9 -> {happy[8] = '1'}
                    R.id.cb_happy10 -> {happy[9] = '1'}
                }
            } else{
                when (buttonView.id) {
                    R.id.cb_happy1 -> {happy[0] = '0'}
                    R.id.cb_happy2 -> {happy[1] = '0'}
                    R.id.cb_happy3 -> {happy[2] = '0'}
                    R.id.cb_happy4 -> {happy[3] = '0'}
                    R.id.cb_happy5 -> {happy[4] = '0'}
                    R.id.cb_happy6 -> {happy[5] = '0'}
                    R.id.cb_happy7 -> {happy[6] = '0'}
                    R.id.cb_happy8 -> {happy[7] = '0'}
                    R.id.cb_happy9 -> {happy[8] = '0'}
                    R.id.cb_happy10 -> {happy[9] = '0'}
                }
            }
        }

        binding.cbHappy1.setOnCheckedChangeListener(happyListener)
        binding.cbHappy2.setOnCheckedChangeListener(happyListener)
        binding.cbHappy3.setOnCheckedChangeListener(happyListener)
        binding.cbHappy4.setOnCheckedChangeListener(happyListener)
        binding.cbHappy5.setOnCheckedChangeListener(happyListener)
        binding.cbHappy6.setOnCheckedChangeListener(happyListener)
        binding.cbHappy7.setOnCheckedChangeListener(happyListener)
        binding.cbHappy8.setOnCheckedChangeListener(happyListener)
        binding.cbHappy9.setOnCheckedChangeListener(happyListener)
        binding.cbHappy10.setOnCheckedChangeListener(happyListener)

        val sadListener = CompoundButton.OnCheckedChangeListener { buttonView,
                                                                   isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.cb_sad1 -> {sad[0] = '1'}
                    R.id.cb_sad2 -> {sad[1] = '1'}
                    R.id.cb_sad3 -> {sad[2] = '1'}
                    R.id.cb_sad4 -> {sad[3] = '1'}
                    R.id.cb_sad5 -> {sad[4] = '1'}
                    R.id.cb_sad6 -> {sad[5] = '1'}
                    R.id.cb_sad7 -> {sad[6] = '1'}
                    R.id.cb_sad8 -> {sad[7] = '1'}
                    R.id.cb_sad9 -> {sad[8] = '1'}
                    R.id.cb_sad10 -> {sad[9] = '1'}
                }
            } else{
                when (buttonView.id) {
                    R.id.cb_sad1 -> {sad[0] = '0'}
                    R.id.cb_sad2 -> {sad[1] = '0'}
                    R.id.cb_sad3 -> {sad[2] = '0'}
                    R.id.cb_sad4 -> {sad[3] = '0'}
                    R.id.cb_sad5 -> {sad[4] = '0'}
                    R.id.cb_sad6 -> {sad[5] = '0'}
                    R.id.cb_sad7 -> {sad[6] = '0'}
                    R.id.cb_sad8 -> {sad[7] = '0'}
                    R.id.cb_sad9 -> {sad[8] = '0'}
                    R.id.cb_sad10 -> {sad[9] = '0'}
                }
            }
        }

        binding.cbSad1.setOnCheckedChangeListener(sadListener)
        binding.cbSad2.setOnCheckedChangeListener(sadListener)
        binding.cbSad3.setOnCheckedChangeListener(sadListener)
        binding.cbSad4.setOnCheckedChangeListener(sadListener)
        binding.cbSad5.setOnCheckedChangeListener(sadListener)
        binding.cbSad6.setOnCheckedChangeListener(sadListener)
        binding.cbSad7.setOnCheckedChangeListener(sadListener)
        binding.cbSad8.setOnCheckedChangeListener(sadListener)
        binding.cbSad9.setOnCheckedChangeListener(sadListener)
        binding.cbSad10.setOnCheckedChangeListener(sadListener)

        val surpriseListener = CompoundButton.OnCheckedChangeListener { buttonView,
                                                                        isChecked ->
            if (isChecked) {
                when (buttonView.id) {
                    R.id.cb_surprise1 -> {surprise[0] = '1'}
                    R.id.cb_surprise2 -> {surprise[1] = '1'}
                    R.id.cb_surprise3 -> {surprise[2] = '1'}
                    R.id.cb_surprise4 -> {surprise[3] = '1'}
                    R.id.cb_surprise5 -> {surprise[4] = '1'}
                    R.id.cb_surprise6 -> {surprise[5] = '1'}
                    R.id.cb_surprise7 -> {surprise[6] = '1'}
                    R.id.cb_surprise8 -> {surprise[7] = '1'}
                    R.id.cb_surprise9 -> {surprise[8] = '1'}
                    R.id.cb_surprise10 -> {surprise[9] = '1'}
                }
            } else{
                when (buttonView.id) {
                    R.id.cb_surprise1 -> {surprise[0] = '0'}
                    R.id.cb_surprise2 -> {surprise[1] = '0'}
                    R.id.cb_surprise3 -> {surprise[2] = '0'}
                    R.id.cb_surprise4 -> {surprise[3] = '0'}
                    R.id.cb_surprise5 -> {surprise[4] = '0'}
                    R.id.cb_surprise6 -> {surprise[5] = '0'}
                    R.id.cb_surprise7 -> {surprise[6] = '0'}
                    R.id.cb_surprise8 -> {surprise[7] = '0'}
                    R.id.cb_surprise9 -> {surprise[8] = '0'}
                    R.id.cb_surprise10 -> {surprise[9] = '0'}
                }
            }
        }

        binding.cbSurprise1.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise2.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise3.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise4.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise5.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise6.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise7.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise8.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise9.setOnCheckedChangeListener(surpriseListener)
        binding.cbSurprise10.setOnCheckedChangeListener(surpriseListener)

        binding.btnRegister.setOnClickListener {
            Log.e("test", "${id},  ${pw}, ${name}, ${sex},  ${email}")
            if (id != null && pw != null && name != null && sex != null && email != null) {
                PostRegister().signUpRegister(
                    id,
                    pw,
                    name,
                    sex,
                    email,
                    String(anger.toCharArray()),
                    String(fear.toCharArray()),
                    String(happy.toCharArray()),
                    String(sad.toCharArray()),
                    String(surprise.toCharArray()),
                    this
                )
            }
        }

        binding.btnBackSign.setOnClickListener {
            onBackPressed()
        }
    }

    private fun start() {
        val intent = Intent(this, SignUpActivity::class.java)
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