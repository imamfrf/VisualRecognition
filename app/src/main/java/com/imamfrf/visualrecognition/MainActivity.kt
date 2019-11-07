package com.imamfrf.visualrecognition

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper
import com.ibm.watson.developer_cloud.service.security.IamOptions
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var cameraHelper: CameraHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cameraHelper = CameraHelper(this)
        button_take_picture.setOnClickListener {
            cameraHelper.dispatchTakePictureIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CameraHelper.REQUEST_IMAGE_CAPTURE) {
                progress_bar.visibility = View.VISIBLE
                val photoFile = cameraHelper.getFile(resultCode)
                Glide.with(applicationContext).load(photoFile).into(imageView)
                imageRecognition(photoFile)
            }
        }
    }

    private fun imageRecognition(file: File) {
        AsyncTask.execute {
            val options = IamOptions.Builder()
                .apiKey(BuildConfig.WATSON_API_KEY)
                .build()
            val visualRecognition = VisualRecognition("2018-03-19", options)

            val classifyOptions = ClassifyOptions.Builder()
                .imagesFile(file)
                .classifierIds(listOf("AnimalCustomModel_1525594914"))
                .build()

            val result = visualRecognition.classify(classifyOptions).execute()
            println(result)
            val className = result.images[0].classifiers[0].classes[0].className

            if (className != null) {
                runOnUiThread {
                    text_view_class.text = className
                    progress_bar.visibility = View.GONE
                }
            }
        }
    }

}
