package com.example.themoviedb.person_details_screen.person_details_presenter;

import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModelInterface;
import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsViewInterface;

public class PersonDetailsPresenter {
    PersonDetailsViewInterface personDetailsViewInterface;
    PersonDetailsModelInterface personDetailsModelInterface ;

    public PersonDetailsPresenter(PersonDetailsViewInterface personDetailsViewInterface, PersonDetailsModelInterface personDetailsModelInterface) {
        this.personDetailsViewInterface = personDetailsViewInterface;
        this.personDetailsModelInterface = personDetailsModelInterface;
        personDetailsModelInterface.setModel(this);
    }

    public void addPersonImages(Profiles personImage) {
        personDetailsViewInterface.setImage (personImage);
    }

    public void setAdapter() {
        personDetailsViewInterface.setImagesInAdapter();
    }

    public void getPersonDetails() {
        personDetailsViewInterface.getPersonDetails();
    }

    public void fetchPersonImage(String s) {
        personDetailsModelInterface.startFetchingImage(s);

    }
}
