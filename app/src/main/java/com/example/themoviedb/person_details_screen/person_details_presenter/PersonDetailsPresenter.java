package com.example.themoviedb.person_details_screen.person_details_presenter;

import android.content.Intent;
import android.os.Bundle;

import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModelInterface;
import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsViewInterface;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;

public class PersonDetailsPresenter {
    PersonDetailsViewInterface personDetailsViewInterface;
    PersonDetailsModelInterface personDetailsModelInterface ;
    PopularPeople popularPeople = new PopularPeople() ;


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

    public PopularPeople getPersonDetails() {
        Intent i = personDetailsViewInterface.getIntent();
        Bundle extras = i.getExtras();

        String name = extras.getString("person_name");
        String dep = extras.getString("person_department");
        int id = extras.getInt("person_id", 1);
        boolean adult = extras.getBoolean("person_adult", true);
        String profile_path = extras.getString("profile_path");

        popularPeople.setName(name);
        popularPeople.setId(id);
        popularPeople.setKnown_for_department(dep);
        popularPeople.setAdult(adult);
        popularPeople.setProfile_path(profile_path);

        return popularPeople;
    }

    public void fetchPersonImage(String s) {
        personDetailsModelInterface.startFetchingImage(s);

    }
}
