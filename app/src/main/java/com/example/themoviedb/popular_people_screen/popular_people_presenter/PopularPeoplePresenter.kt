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
    var peopleList: MutableList<PopularPeople>? = null
    internal var page = 1
    lateinit var searchStr : String

    fun callFetchingData() {
        val asyncFetch = PopularPeopleModel.AsyncFetch(object : AsyncResponseInterface {
            override fun processFinish(popularPeople: PopularPeople) {
                popularPeopleViewInterface.setPerson(popularPeople)
                settingAdapter()
            }
        } , page)
//        urlString = s
        asyncFetch.execute()
    }

    fun settingAdapter() {
        popularPeopleViewInterface.settingAdapterInList()
    }

    fun callSwipeFun() {

        peopleList = popularPeopleViewInterface.getList()!!.toMutableList()
        this.clearList()

        searchState = popularPeopleViewInterface.getSearchState()
        if (searchState!!) {
            page = 1
            callFetchingData()

        } else {
            page = 1
            callFetchingData()
        }
    }

    fun clearList() {
        peopleList = popularPeopleViewInterface.getList() as MutableList<PopularPeople>?
        val size = peopleList?.size
        if (size != null) {
            if (size > 0) {
                for (i in 0 until size) {
                    peopleList?.removeAt(0)
                }
                popularPeopleViewInterface.notifyDataRemoved(size)
            }
        }
    }

    fun callScrollingFun() {

        searchState = popularPeopleViewInterface.getSearchState()

        if (searchState == true) {
            utilities.searchPage = utilities.searchPage + 1
            searchPageStr = utilities.searchPage.toString()

            callFetchingData()

        } else {
            page = page + 1
//            utilities.popularPeoplePage = utilities.popularPeoplePage + 1
//            pageStr = utilities.popularPeoplePage.toString()
            callFetchingData()
        }
        searchState = false
    }

    fun searchData(s: String) {
        searchState = popularPeopleViewInterface.getSearchState()
        this.sendSearcDataToMobel (s , searchState)

//        this.callFetchingData()
        val asyncFetch = PopularPeopleModel.AsyncFetch(object : AsyncResponseInterface {
            override fun processFinish(popularPeople: PopularPeople) {
                popularPeopleViewInterface.setPerson(popularPeople)
                settingAdapter()
            }
        } , page)
//        urlString = s
        asyncFetch.execute()

    }

    private fun sendSearcDataToMobel(s: String, searchState: Boolean?) {
        popularPeopleModelInterface.sendSearchData(s , searchState)
    }
}