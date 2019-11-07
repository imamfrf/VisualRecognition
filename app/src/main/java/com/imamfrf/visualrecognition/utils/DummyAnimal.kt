package com.imamfrf.visualrecognition.utils

import com.imamfrf.visualrecognition.model.Animal

object DummyAnimal {
    private val animalList = arrayListOf(
        Animal(
            "Kucing",
            "Felis Catus",
            "Mamalia",
            "Karnivora"
        ),
        Animal(
            "Anjing",
            "Canis Lupus",
            "Mamalia",
            "Karnivora"
        )
    )

    fun getAnimal(name: String): Animal?{
        for (i in 0 until animalList.size){
            if (animalList[i].name == name){
                return animalList[i]
            }
        }
        return null
    }
}