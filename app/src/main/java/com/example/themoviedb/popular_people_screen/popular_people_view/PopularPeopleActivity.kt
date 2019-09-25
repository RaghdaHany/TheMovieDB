package com.example.themoviedb.popular_people_screen.popular_people_view

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar

import com.example.themoviedb.R
import com.example.themoviedb.others.Utilities
import com.example.themoviedb.person_details_screen.person_details_view.PersonDetailsActivity
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModel
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople
import java.util.ArrayList

class PopularPeopleActivity : AppCompatActivity(), android.widget.SearchView.OnQueryTextListener, PopularPeopleViewInterface {

    internal var isScrolling: Boolean? = false
    internal var currentItems: Int = 0
    internal var totalItems: Int = 0
    internal var scrollingOutItems: Int = 0
    var page = 1
    private var recyclerView: RecyclerView? = null
    private var popularPeopleAdapter: PopularPeopleAdapter? = null
    private var layoutManager: LinearLayoutManager? = null
    internal lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private var popularPeopleList: MutableList<PopularPeople>? = null
    internal lateinit var progressBar: ProgressBar
    internal lateinit var searchView: android.widget.SearchView

    var isSearchAction: Boolean? = false
    private var searchstr = ""
    private var pageStr = ""
    internal lateinit var menuItem: MenuItem

    internal lateinit var popularPeoplePresenter: PopularPeoplePresenter
    internal lateinit var utilities: Utilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popular_people)

        recyclerView = findViewById<View>(R.id.recyclerViewId) as RecyclerView
        progressBar = findViewById(R.id.progress_bar_id)
        layoutManager = LinearLayoutManager(this)
        popularPeopleList = ArrayList()
        popularPeopleAdapter = PopularPeopleAdapter(this@PopularPeopleActivity, popularPeopleList!!)
        recyclerView!!.adapter = popularPeopleAdapter

        utilities = Utilities()
        pageStr = page.toString()
        popularPeoplePresenter = PopularPeoplePresenter(this, PopularPeopleModel())

        popularPeoplePresenter.callFetchingData()

        mSwipeRefreshLayout = findViewById<View>(R.id.swipe_container) as SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)

        mSwipeRefreshLayout.setOnRefreshListener {
            popularPeoplePresenter.callSwipeFun()
            mSwipeRefreshLayout.isRefreshing = false
        }

        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                currentItems = layoutManager!!.childCount
                scrollingOutItems = layoutManager!!.findFirstVisibleItemPosition()
                totalItems = layoutManager!!.itemCount
                if (isScrolling!! && currentItems + scrollingOutItems == totalItems) {
                    isScrolling = false
                    progressBar.visibility = View.VISIBLE
                    popularPeoplePresenter.callScrollingFun()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        menuItem = menu.findItem(R.id.search_btn)
        searchView = menuItem.actionView as android.widget.SearchView
        searchView.setOnQueryTextListener(this)
        searchView.clearFocus()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(s: String): Boolean {
        if (searchView.query.length == 0) {
            isSearchAction = false
            searchView.clearFocus()
            popularPeoplePresenter.callFetchingData()
        }
        return true
    }

    override fun onQueryTextSubmit(s: String): Boolean {
        searchstr = s
        if (s != "") {
            popularPeoplePresenter.clearList(popularPeopleList!!)
            isSearchAction = true
            popularPeoplePresenter.searchData(s)
        }
        searchView.clearFocus()
        return true
    }

    override fun setPerson(person: PopularPeople) {
        popularPeopleList!!.add(person)
    }

    override fun settingAdapterInList() {
        popularPeopleAdapter!!.notifyDataSetChanged()
        recyclerView!!.layoutManager = layoutManager
    }

    override fun getList(): List<PopularPeople>? {
        return popularPeopleList
    }

    override fun notifyDataRemoved(size: Int) {
        popularPeopleAdapter!!.notifyItemRangeRemoved(0, size)

    }

    override fun getSearchState(): Boolean? {
        return isSearchAction
    }
}