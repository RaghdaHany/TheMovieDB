package com.example.themoviedb.person_details_screen.person_details_presenter

import com.example.themoviedb.person_details_screen.person_details_model.AsyncFetchinImagesInterface
import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModel
import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModelInterface
import com.example.themoviedb.person_details_screen.person_details_model.Profiles
import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsViewInterface

class PersonDetailsPresenter(internal var personDetailsViewInterface: PersonDetailsViewInterface, internal var personDetailsModelInterface: PersonDetailsModelInterface) {

    fun getPersonDetails() {
        personDetailsViewInterface.getPersonDetails()
    }

    fun fetchPersonImage(s: String) {
        val getPhotos = PersonDetailsModel.GetPhotos(object : AsyncFetchinImagesInterface {
            override fun processFinish(personImage: Profiles) {
                personDetailsViewInterface.setImage(personImage)
                personDetailsViewInterface.setImagesInAdapter()
            }
        })
        getPhotos.execute(s)
    }
}
