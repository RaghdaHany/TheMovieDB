package com.example.themoviedb.person_details_screen.person_details_controller;

import android.content.Intent;

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


    public void addPersonDetails(Profiles personDetails) {

    }

    public void setAdapter() {
    }

    public PopularPeople getPersonDetails() {

        Intent i = personDetailsActivity.getIntent();
        String name = i.getStringExtra("person_name");
        String dep = i.getStringExtra("person_department");
        int id = i.getIntExtra("id", 1);
        boolean adult = i.getBooleanExtra("person_adult", true);
        String profile_path = i.getStringExtra("profile_path");

        popularPeople.setName(name);
        popularPeople.setId(id);
        popularPeople.setKnown_for_department(dep);
        popularPeople.setAdult(adult);
        popularPeople.setProfile_path(profile_path);

        return popularPeople;
    }
}
