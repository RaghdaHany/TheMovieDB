package com.example.themoviedb.popular_people_screen.popular_people_presenter

import com.example.themoviedb.others.Utilities
import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncResponseInterface
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModel
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModelInterface
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleViewInterface

class PopularPeoplePresenter(internal var popularPeopleViewInterface: PopularPeopleViewInterface, internal var popularPeopleModelInterface: PopularPeopleModelInterface) {

    internal var utilities = Utilities()
    internal lateinit var urlString: String
    internal var pageStr = ""
    internal var searchPageStr = ""
    internal var searchState: Boolean? = null
    internal lateinit var peopleList: MutableList<PopularPeople>

    fun callFetchingData(s: String) {

        val asyncFetch = PopularPeopleModel.AsyncFetch(object : AsyncResponseInterface {
            override fun processFinish(popularPeople: PopularPeople) {
                popularPeopleViewInterface.setPerson(popularPeople)
                settingAdapter()
            }
        })
        urlString = s
        asyncFetch.execute(s)
    }

    fun settingAdapter() {
        popularPeopleViewInterface.settingAdapterInList()
    }

    fun callSwipeFun() {

        peopleList = popularPeopleViewInterface.getList()!!.toMutableList()
        this.clearList(peopleList)

        searchState = popularPeopleViewInterface.getSearchState()
        if (searchState!!) {
            callFetchingData(urlString + utilities.pageURL + utilities.firstPage)
            utilities.searchPage = 1

        } else {
            callFetchingData(utilities.popularPeopleURL + utilities.firstPage)
            utilities.popularPeoplePage = 1
        }
    }

    fun clearList(peopleList: MutableList<*>) {
        val size = peopleList.size
        if (size > 0) {
            for (i in 0 until size) {
                peopleList.removeAt(0)
            }
            popularPeopleViewInterface.notifyDataRemoved(size)
        }
    }

    fun callScrollingFun() {

        searchState = popularPeopleViewInterface.getSearchState()

        if (searchState!!) {
            utilities.searchPage = utilities.searchPage + 1
            searchPageStr = utilities.searchPage.toString()
            callFetchingData(urlString + utilities.pageURL + searchPageStr)

        } else {
            utilities.popularPeoplePage = utilities.popularPeoplePage + 1
            pageStr = utilities.popularPeoplePage.toString()
            callFetchingData(utilities.popularPeopleURL + pageStr)
        }
    }

    fun searchData(s: String) {
        this.callFetchingData(s)
    }
}