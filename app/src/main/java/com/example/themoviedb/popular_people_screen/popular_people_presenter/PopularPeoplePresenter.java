package com.example.themoviedb.popular_people_screen.popular_people_presenter;

import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncResponseInterface;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModel;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModelInterface;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleViewInterface;

import java.util.List;

public class PopularPeoplePresenter {

    Utilities utilities = new Utilities();
    String urlString ;
    String pageStr = "";
    String searchPageStr = "";
    PopularPeopleViewInterface popularPeopleViewInterface;
    PopularPeopleModelInterface popularPeopleModelInterface ;
    Boolean searchState ;
    List<PopularPeople> peopleList ;


    public PopularPeoplePresenter(PopularPeopleViewInterface popularPeopleViewInterface, PopularPeopleModelInterface popularPeopleModelInterface) {
        this.popularPeopleViewInterface = popularPeopleViewInterface;
        this.popularPeopleModelInterface = popularPeopleModelInterface;
    }

    public void callFetchingData(String s) {

        PopularPeopleModel.AsyncFetch asyncFetch = new PopularPeopleModel.AsyncFetch (new AsyncResponseInterface() {

            @Override
            public void processFinish(PopularPeople popularPeople) {
                popularPeopleViewInterface.setPerson(popularPeople);
                settingAdapter();
            }
        });
        urlString = s ;
        asyncFetch.execute(s);
    }

    public void settingAdapter() {
        popularPeopleViewInterface.settingAdapterInList ();
    }

    public void callSwipeFun() {

        peopleList = popularPeopleViewInterface.getList();
        this.clearList (peopleList);

        searchState = popularPeopleViewInterface.getSearchState();
        if (searchState) {
            callFetchingData(urlString + utilities.pageURL + utilities.firstPage );
            utilities.searchPage = 1 ;

        } else {
            callFetchingData(utilities.popularPeopleURL + utilities.firstPage);
            utilities.popularPeoplePage = 1 ;
        }
    }

    public void clearList(List peopleList) {
        int size = peopleList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                peopleList.remove(0);
            }
            popularPeopleViewInterface.notifyDataRemoved(size);
        }
    }

    public void callScrollingFun() {

        searchState = popularPeopleViewInterface.getSearchState();

        if (searchState) {
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