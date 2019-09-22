package com.example.themoviedb.person_details_screen.person_details_presenter;

import com.example.themoviedb.person_details_screen.person_details_model.AsyncFetchinImagesInterface;
import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModel;
import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModelInterface;
import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsViewInterface;

public class PersonDetailsPresenter {
    PersonDetailsViewInterface personDetailsViewInterface;
    PersonDetailsModelInterface personDetailsModelInterface ;

    public PersonDetailsPresenter(PersonDetailsViewInterface personDetailsViewInterface, PersonDetailsModelInterface personDetailsModelInterface) {
        this.personDetailsViewInterface = personDetailsViewInterface;
        this.personDetailsModelInterface = personDetailsModelInterface;
    }

    public void getPersonDetails() {
        personDetailsViewInterface.getPersonDetails();
    }

    public void fetchPersonImage(String s) {
        PersonDetailsModel.GetPhotos getPhotos=new PersonDetailsModel.GetPhotos(new AsyncFetchinImagesInterface() {

            @Override
            public void processFinish(Profiles personImage) {
                personDetailsViewInterface.setImage(personImage);
                personDetailsViewInterface.setImagesInAdapter();
            }
        });
        getPhotos.execute(s);
    }
}
