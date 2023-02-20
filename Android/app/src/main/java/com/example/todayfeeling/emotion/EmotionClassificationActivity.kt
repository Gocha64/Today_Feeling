package com.example.todayfeeling.emotion

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.todayfeeling.MainActivity
import com.example.todayfeeling.emotion.camerax.CameraManager
import com.example.todayfeeling.databinding.ActivityEmotionClassificationBinding
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class EmotionClassificationActivity : AppCompatActivity() {

    private var mBinding: ActivityEmotionClassificationBinding? = null
    private val binding get() = mBinding!!
    private lateinit var cameraManager: CameraManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEmotionClassificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tflite = getTFliteInterpreter("model.tflite")
        createCameraManager(tflite)
        checkForPermission()
        onClicks()
    }

    private fun getTFliteInterpreter(s: String): Interpreter {
        return Interpreter(loadModelFile(this, s))
    }

    private fun loadModelFile(mainActivity: EmotionClassificationActivity, s: String): ByteBuffer {
        val fileDescriptor = mainActivity.assets.openFd(s)
        val inputStream: FileInputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel: FileChannel = inputStream.channel
        var startOffset = fileDescriptor.startOffset
        var declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun checkForPermission() {
        if (allPermissionsGranted()) {
            cameraManager.startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun onClicks() {
        binding.btnSwitch.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                cameraManager.startCamera()
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT)
                    .show()
                finish()
            }
        }
    }

    private fun createCameraManager(interpreter: Interpreter) {
        cameraManager = CameraManager(
            this,
            binding.previewViewFinder,
            this,
            binding.graphicOverlayFinder,
            interpreter,
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("destroy", "들어옴")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("restart", "재시작함")
    }

    override fun onStop() {
        super.onStop()
        finish()
        Log.e("stop", "멈춤")
    }

    override fun onPause() {
        super.onPause()
        Log.e("pause", "pause")
    }
}