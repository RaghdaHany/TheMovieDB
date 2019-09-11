package com.example.themoviedb.popular_people_screen.popular_people_controller;

import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncFetch;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleActivity;

public class PopularPeopleController {

    PopularPeopleActivity popularPeopleActivity;
    AsyncFetch asyncFetch = new AsyncFetch(this);
    Utilities utilities = new Utilities();
    String pageStr = "";


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

        popularPeopleActivity.clearList ();
        callFetchingData(utilities.popularPeopleURL + utilities.firstPage);
//        if (isSearchAction) {
//            searchPage=1;
//            searchPageStr = String.valueOf(searchPage);
//            new AsyncFetch().execute(search_url+searchstr+"&page="+searchPageStr);
//        }
//        else {
//            page=1;
//            pageStr = String.valueOf(page);
//            new AsyncFetch().execute(data_url+pageStr);
        }

    public void callScrollingFun() {
        utilities.page = utilities.page + 1 ;
        pageStr = String.valueOf(utilities.page);
        callFetchingData(utilities.popularPeopleURL + pageStr);

    }
}

