package com.example.themoviedb.popular_people_screen.popular_people_model

import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter

interface PopularPeopleModelInterface//    void startFetching(String s);
{
    fun sendSearchData(s: String, searchState: Boolean?)
}
//    void setModel(PopularPeoplePresenter popularPeoplePresenter);
