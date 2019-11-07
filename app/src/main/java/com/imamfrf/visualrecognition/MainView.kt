package com.imamfrf.visualrecognition

import com.imamfrf.visualrecognition.model.Animal

interface MainView {
    fun onSuccess(animal: Animal)
    fun onFailure()
}