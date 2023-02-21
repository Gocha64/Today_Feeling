package com.example.todayfeeling.emotion.face_detection

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.util.Log
import com.example.todayfeeling.emotion.YoutubeActivity
import com.example.todayfeeling.emotion.camerax.BaseImageAnalyzer
import com.example.todayfeeling.emotion.camerax.GraphicOverlay

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import org.tensorflow.lite.Interpreter
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class FaceContourDetectionProcessor(private val view: GraphicOverlay, private val model: Interpreter, private val context: Context) :
    BaseImageAnalyzer<List<Face>>() {

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)

    override val graphicOverlay: GraphicOverlay
        get() = view

    override fun detectInImage(image: InputImage): Task<List<Face>> {
        return detector.process(image)
    }

    override fun classificationEmotion(image: Bitmap): String {
        val output = Array(1){FloatArray(6)}
        val input = convertBitmapGrayByteBuffer(image)
        model.run(input, output)
        var result: String
        when (argmax(output[0])) {
            0 -> {
                result = "화남"
            }
            1 -> {
                result = "공포"
            }
            2 -> {
                result = "행복"
            }
            3 -> {
                result = "슬픔"
            }
            4 -> {
                result = "놀람"
            }
            5 -> {
                result = "무표정"
            }
            else -> {
                result = "무표정"
            }
        }
        return result
    }

    override fun isResultNotBlank(result: String) {
        var resultEmotion: Int
        when (result) {
            "화남" -> {
                resultEmotion = 1
            }
            "공포" -> {
                resultEmotion = 2
            }
            "행복" -> {
                resultEmotion = 3
            }
            "슬픔" -> {
                resultEmotion = 4
            }
            "놀람" -> {
                resultEmotion = 5
            }
            else -> {
                resultEmotion = 0
            }
        }
        if (resultEmotion != 0) {
            val intent = Intent(context, YoutubeActivity::class.java)
            intent.putExtra("emotion", resultEmotion)
            stop()
            context.startActivity(intent)
        }
    }

    fun convertBitmapGrayByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(bitmap.byteCount)
        byteBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        pixels.forEach { pixel ->
            val r = pixel shr 16 and 0xFF
            val g = pixel shr 8 and 0xFF
            val b = pixel and 0xFF

            val avgPixelValue = (r + g + b) / 3.0f
            val normalizedPixelValue = avgPixelValue / 255.0f

            byteBuffer.putFloat(normalizedPixelValue)
            // rgb 정규화
//        화   byteBuffer.putFloat((r - 128.0f) / 128.0f)
//            byteBuffer.putFloat((g - 128.0f) / 128.0f)
//            byteBuffer.putFloat((b - 128.0f) / 128.0f)
        }
        return byteBuffer
    }

    private fun argmax(probs: FloatArray): Int {
        var maxIdx = -1
        var maxProb = 0.0f

        Log.e("예측값", "${probs[0]},  ${probs[1]},  ${probs[2]},  ${probs[3]},  ${probs[4]},  ${probs[5]}")

        for (i in probs.indices) {
            if (probs[i] > maxProb) {
                maxProb = probs[i]
                maxIdx = i
            }
        }
        return maxIdx
    }

    override fun stop() {
        try {
            detector.close()
        } catch (e: IOException) {
            Log.e(TAG, "Exception thrown while trying to close Face Detector: $e")
        }
    }

    override fun onSuccess(
        results: List<Face>,
        graphicOverlay: GraphicOverlay,
        rect: Rect
    ) {
        graphicOverlay.clear()
        results.forEach {
            val faceGraphic = FaceContourGraphic(graphicOverlay, it, rect)
            graphicOverlay.add(faceGraphic)
        }
        graphicOverlay.postInvalidate()
    }

    override fun onFailure(e: Exception) {
        Log.w(TAG, "Face Detector failed.$e")
    }

    companion object {
        private const val TAG = "FaceDetectorProcessor"
    }

}