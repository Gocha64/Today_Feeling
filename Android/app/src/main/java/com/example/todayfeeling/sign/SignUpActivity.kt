package com.example.todayfeeling.sign

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.todayfeeling.data.ResultData
import com.example.todayfeeling.data.SignUpData
import com.example.todayfeeling.databinding.ActivitySignUpBinding
import com.example.todayfeeling.network.RetrofitImpl
import com.google.android.gms.common.util.Base64Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.DigestException
import java.security.MessageDigest
import kotlin.experimental.and

class SignUpActivity : AppCompatActivity() {
    private var mBinding: ActivitySignUpBinding? = null
    private val binding get() = mBinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            SignUpRegister(binding.editSignupId.text.toString(), binding.editSignupPw.text.toString(),
                binding.editSignupName.text.toString(), binding.editSignupSex.text.toString(), this)

        }
    }

    fun SignUpRegister(id: String, pw: String, name: String, sex: String, context: Context) {
        val signupData = SignUpData(id,pw,name,sex)
        Log.d("test", signupData.id)
        Log.d("test", signupData.pw)
        Log.d("test", signupData.name)
        Log.d("test", signupData.sex)
        Log.d("test", signupData.toString())

        val call = RetrofitImpl.service.postSignUp(signupData)
        val dialog = AlertDialog.Builder(context)
            .setTitle("회원가입 시도중")
            .setMessage("잠시만 기다려주세요.")
            .create()
        dialog.show()

        call.enqueue(object: Callback<ResultData> {
            override fun onResponse(call: Call<ResultData>, response: Response<ResultData>) {
                Log.d("signup","$response")
                if (response.body()?.result.toString() == "success") {
                    dialog.dismiss()
                    Log.d("test", "${response.headers().toString()}")
                    val intent = Intent(context, LoginActivity::class.java)
                    context.startActivity(intent)
                } else {
                    dialog.dismiss()
                    Log.d("test", "${response.body()}")
                    Log.d("test","무엇인가 잘못됨")
                }
            }

            override fun onFailure(call: Call<ResultData>, t: Throwable) {
                Log.d("test","$t")
                Log.d("test","무엇인가 잘못됨1111111")
            }

        })

    }
//    fun getSign(input: String): String{
//        val hash: ByteArray
//        try{
//            val md = MessageDigest.getInstance("SHA-1")
//            md.update(input.toByteArray())
//            hash = md.digest()
//        }catch (e: CloneNotSupportedException){
//            throw DigestException("couldn't make digest of patial content")
//        }
//        Log.d("test",hash.toString())
//        return Base64Utils.encode(hash)
//    }
}