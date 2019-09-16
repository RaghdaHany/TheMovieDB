package com.example.themoviedb.popular_people_screen.popular_people_view;

import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;

import java.util.List;

public interface PopularPeopleViewInterface {

    void setPerson(PopularPeople person);
    void settingAdapterInList();
    List<PopularPeople> getList();
    Boolean getSearchState();
    void notifyDataRemoved(int size);
}
