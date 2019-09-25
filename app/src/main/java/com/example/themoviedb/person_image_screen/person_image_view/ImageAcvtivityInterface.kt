package com.example.themoviedb.person_image_screen.person_image_view

import android.content.Intent

interface ImageAcvtivityInterface {

    fun sendPhotoStringToActivity(photo: String)

    fun savePhoto()

    fun setBroadcast(mediaScanIntent: Intent)

    fun getPermission()

    fun getPicturePath()
}
