package com.example.themoviedb.popular_people_screen.popular_people_controller;

import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncFetch;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleActivity;

public class PopularPeopleController {

    PopularPeopleActivity popularPeopleActivity;
    AsyncFetch asyncFetch = new AsyncFetch(this);
    Utilities utilities = new Utilities();
    String urlString ;
    String pageStr = "";
    String searchPageStr = "";



    public PopularPeopleController(PopularPeopleActivity popularPeopleActivity, AsyncFetch asyncFetch) {
        this.popularPeopleActivity = popularPeopleActivity;
        asyncFetch.setModel(this);
        this.asyncFetch = asyncFetch;
    }

    public PopularPeopleController(PopularPeopleActivity popularPeopleActivity) {
        this.popularPeopleActivity = popularPeopleActivity;
        asyncFetch.setModel(this);
        this.asyncFetch = asyncFetch;
    }

    public PopularPeopleController(AsyncFetch asyncFetch) {
        this.asyncFetch = asyncFetch;
    }

    public void callFetchingData(String s) {
        urlString = s ;
        asyncFetch.startFetching (s);
//        asyncFetch.execute(s);
    }


    public void addingPerson(PopularPeople person) {
        popularPeopleActivity.setPerson(person);
    }

    public void settingAdapter() {
        popularPeopleActivity.settingAdapterInList ();
    }

    public void callSwipeFun() {

        popularPeopleActivity.clearList();
        if (popularPeopleActivity.isSearchAction) {
            callFetchingData(urlString + utilities.pageURL + utilities.firstPage );
            utilities.searchPage = 1 ;

        } else {
            callFetchingData(utilities.popularPeopleURL + utilities.firstPage);
            utilities.popularPeoplePage = 1 ;
        }
    }
    public void callScrollingFun() {


        if (popularPeopleActivity.isSearchAction) {
            utilities.searchPage = utilities.searchPage + 1 ;
            searchPageStr = String.valueOf(utilities.searchPage);
            callFetchingData(urlString + utilities.pageURL + searchPageStr);

        } else {
            utilities.popularPeoplePage = utilities.popularPeoplePage + 1 ;
            pageStr = String.valueOf(utilities.popularPeoplePage);
            callFetchingData(utilities.popularPeopleURL + pageStr);
        }
    }
}

