package com.example.themoviedb.person_details_screen.person_details_controller;

import android.content.Intent;
import android.os.Bundle;

import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModel;
import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsActivity;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;

public class PersonDetailsController {
    PersonDetailsActivity personDetailsActivity;
    PersonDetailsModel personDetailsModel = new PersonDetailsModel(this);
    PopularPeople popularPeople = new PopularPeople() ;


    public PersonDetailsController(PersonDetailsActivity personDetailsActivity) {
        this.personDetailsActivity = personDetailsActivity;
        personDetailsModel.setModel(this);
        this.personDetailsModel = personDetailsModel;
    }


    public void addPersonImages(Profiles personImage) {
        personDetailsActivity.setImage (personImage);
    }

    public void setAdapter() {
        personDetailsActivity.setImagesInAdapter();
    }

    public PopularPeople getPersonDetails() {


        Intent i = personDetailsActivity.getIntent();
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
        personDetailsModel.startFetchingImage(s);

    }
}
