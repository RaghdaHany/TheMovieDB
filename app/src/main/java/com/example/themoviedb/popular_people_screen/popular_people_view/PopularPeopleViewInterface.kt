package com.example.themoviedb.popular_people_screen.popular_people_view

import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople

interface PopularPeopleViewInterface {
    fun getList(): List<PopularPeople>?
    fun getSearchState(): Boolean?

    fun setPerson(person: PopularPeople)
    fun settingAdapterInList()
    fun notifyDataRemoved(size: Int)
}
