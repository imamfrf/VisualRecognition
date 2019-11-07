package com.imamfrf.visualrecognition

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ibm.watson.developer_cloud.android.library.camera.CameraHelper
import com.imamfrf.visualrecognition.model.Animal
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var cameraHelper: CameraHelper
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this)

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
                presenter.imageRecognition(photoFile, this)
            }
        }
    }

//    private fun imageRecognition(file: File) {
//        AsyncTask.execute {
//            val options = IamOptions.Builder()
//                .apiKey(BuildConfig.WATSON_API_KEY)
//                .build()
//            val visualRecognition = VisualRecognition("2018-03-19", options)
//
//            val classifyOptions = ClassifyOptions.Builder()
//                .imagesFile(file)
//                .classifierIds(listOf("AnimalCustomModel_1525594914"))
//                .build()
//
//            val result = visualRecognition.classify(classifyOptions).execute()
//            println(result)
//            val className = result.images[0].classifiers[0].classes[0].className
//
//            if (className != null) {
//                runOnUiThread {
//                    text_view_name.text = className
//                    progress_bar.visibility = View.GONE
//                }
//            }
//        }
//    }

    override fun onSuccess(animal: Animal) {
        progress_bar.visibility = View.GONE
        text_view_name.text = animal.name
        text_view_species.text = animal.species
        text_view_detail.text = "Kelas : ${animal.animalClass}\nOrdo : ${animal.ordo}"
    }
}
