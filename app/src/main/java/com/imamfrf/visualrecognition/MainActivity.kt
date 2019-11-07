package com.imamfrf.visualrecognition

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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


    override fun onSuccess(animal: Animal) {
        progress_bar.visibility = View.GONE
        text_view_name.text = animal.name
        text_view_species.text = animal.species
        text_view_detail.text = "Kelas : ${animal.animalClass}\nOrdo : ${animal.ordo}"
    }

    override fun onFailure() {
        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
    }
}
