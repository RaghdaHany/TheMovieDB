package com.example.themoviedb.person_details_screen.person_details_view

import android.content.Intent

import com.example.themoviedb.person_details_screen.person_details_model.Profiles

interface PersonDetailsViewInterface {

    fun setImage(personImage: Profiles)

    fun setImagesInAdapter()

    fun getPersonDetails()
}
