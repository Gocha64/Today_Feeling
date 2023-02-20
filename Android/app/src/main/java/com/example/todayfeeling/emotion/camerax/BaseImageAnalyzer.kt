package com.example.todayfeeling.emotion.camerax

import android.annotation.SuppressLint
import android.graphics.*
import android.media.Image
import android.util.Log
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import java.io.ByteArrayOutputStream


abstract class BaseImageAnalyzer<T> : ImageAnalysis.Analyzer {

    companion object {
        var surprise = false
    }

    abstract val graphicOverlay: GraphicOverlay
    private var lastAnalyzedTimestamp = 0L

    @SuppressLint("UnsafeExperimentalUsageError")
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        var result = "무표정"
        mediaImage?.let {
            detectInImage(InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees))
                .addOnSuccessListener { results ->
                    onSuccess(
                        results,
                        graphicOverlay,
                        it.cropRect
                    )
                    val bitmapImage = mediaImage.toBitmap()
                    (results as List<Face>).forEach {
                        val image =
                            Bitmap.createBitmap(
                                bitmapImage,
                                it.boundingBox.left,
                                it.boundingBox.top,
                                it.boundingBox.width(),
                                it.boundingBox.height()
                            )
                        image.reconfigure(48,48, Bitmap.Config.ARGB_8888)
                        result = classificationEmotion(image)
                        Log.e("test", "$result")
                    }
                    imageProxy.close()
//                        isResultNotBlank(result)
//                        result = "무표정"
                }
                .addOnFailureListener {
                    onFailure(it)
                    imageProxy.close()
                }
        }
    }

    abstract fun isResultNotBlank(result: String)

    abstract fun classificationEmotion(image: Bitmap): String

    private fun Image.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer // Y
        val vuBuffer = planes[2].buffer // VU
        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)
        
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    protected abstract fun detectInImage(image: InputImage): Task<T>

    abstract fun stop()

    protected abstract fun onSuccess(
        results: T,
        graphicOverlay: GraphicOverlay,
        rect: Rect
    )

    protected abstract fun onFailure(e: Exception)

}