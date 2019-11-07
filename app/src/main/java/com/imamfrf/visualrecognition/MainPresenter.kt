package com.imamfrf.visualrecognition

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.view.View
import com.ibm.watson.developer_cloud.service.security.IamOptions
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions
import com.imamfrf.visualrecognition.utils.DummyAnimal
import java.io.File

class MainPresenter(val view: MainView) {

    fun imageRecognition(file: File, activity: Activity){
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
                activity.runOnUiThread {
                    view.onSuccess(DummyAnimal.getAnimal(className)!!)
                }
            }
        }

    }
}