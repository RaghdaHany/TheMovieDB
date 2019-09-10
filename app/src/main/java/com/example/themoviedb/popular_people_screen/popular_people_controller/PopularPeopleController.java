package com.example.themoviedb.popular_people_screen.popular_people_controller;

import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncFetch;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleActivity;

public class PopularPeopleController {
    PopularPeopleActivity popularPeopleActivity;
    AsyncFetch asyncFetch ;

    public PopularPeopleController(PopularPeopleActivity popularPeopleActivity) {
        this.popularPeopleActivity = popularPeopleActivity;
        asyncFetch = new AsyncFetch();
        asyncFetch.setModel(this);
    }

    public void callFetchingData(String s) {
        asyncFetch.startFetching (s);
//        asyncFetch.execute(s);
    }


    public void addingPerson(PopularPeople person) {
        popularPeopleActivity.setPerson(person);
    }

    public void settingAdapter() {
        popularPeopleActivity.settingAdapterInList ();
    }
}
