package com.example.themoviedb.person_image_screen.person_image_presenter

import com.example.themoviedb.person_image_screen.person_image_model.PersonImageModelInterface
import com.example.themoviedb.person_image_screen.person_image_view.ImageAcvtivityInterface

class PersonImagePresenter(internal var imageAcvtivityInterface: ImageAcvtivityInterface, internal var personImageModelInterface: PersonImageModelInterface) {

    fun getPicturePath() {
        imageAcvtivityInterface.getPicturePath()

    }

    fun getPermission() {
        imageAcvtivityInterface.getPermission()
        saveImage()
    }

    private fun saveImage() {
        imageAcvtivityInterface.savePhoto()
    }
}
