package com.example.themoviedb.popular_people_screen.popular_people_model;

import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter;

public interface PopularPeopleModelInterface {
    void startFetching(String s);

    void setModel(PopularPeoplePresenter popularPeoplePresenter);
}
