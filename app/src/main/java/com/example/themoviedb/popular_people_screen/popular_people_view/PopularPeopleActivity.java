package com.example.themoviedb.popular_people_screen.popular_people_view;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.themoviedb.R;
import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModel;
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import java.util.ArrayList;
import java.util.List;

public class PopularPeopleActivity extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener , PopularPeopleViewInterface {

    Boolean isScrolling = false ;
    int currentItems , totalItems , scrollingOutItems ;
    public int  page = 1 ;
    private RecyclerView recyclerView;
    private PopularPeopleAdapter popularPeopleAdapter;
    private LinearLayoutManager layoutManager ;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PopularPeople> popularPeopleList ;
    ProgressBar progressBar;
    android.widget.SearchView searchView;

    public Boolean isSearchAction= false;
    private String searchstr = "";
    private String pageStr="";
    MenuItem menuItem;

    PopularPeoplePresenter popularPeoplePresenter ;
    Utilities utilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_people);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewId);
        progressBar = findViewById(R.id.progress_bar_id);
        layoutManager = new LinearLayoutManager(this);
        popularPeopleList =new ArrayList<>();
        popularPeopleAdapter = new PopularPeopleAdapter(PopularPeopleActivity.this, popularPeopleList);
        recyclerView.setAdapter(popularPeopleAdapter);

        utilities = new Utilities();
        pageStr = String.valueOf(page);
        popularPeoplePresenter = new PopularPeoplePresenter(this , new PopularPeopleModel());

        popularPeoplePresenter.callFetchingData (utilities.popularPeopleURL+pageStr);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                popularPeoplePresenter.callSwipeFun ();
              mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = true ;
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = layoutManager.getChildCount();
                scrollingOutItems = layoutManager.findFirstVisibleItemPosition();
                totalItems = layoutManager.getItemCount();
                if (isScrolling && (currentItems + scrollingOutItems == totalItems)) {
                    isScrolling = false;
                    progressBar.setVisibility(View.VISIBLE);
                    popularPeoplePresenter.callScrollingFun();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        menuItem = menu.findItem(R.id.search_btn);
        searchView = (android.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.clearFocus();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (searchView.getQuery().length() == 0) {
            isSearchAction = false;
            popularPeopleList.clear();
            searchView.clearFocus();
            popularPeoplePresenter.callFetchingData (utilities.popularPeopleURL+pageStr);
        }
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchstr = s;
        if(!s.equals("")) {
            popularPeoplePresenter.clearList(popularPeopleList);
            isSearchAction = true;
            popularPeoplePresenter.callFetchingData (utilities.search_url + s);  ;
        }
        searchView.clearFocus();
        return true;
    }

    public void setPerson(PopularPeople person) {
        popularPeopleList.add(person);
    }

    public void settingAdapterInList() {
        popularPeopleAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public List<PopularPeople> getList() {
        return popularPeopleList ;
    }

    @Override
    public void notifyDataRemoved (int size) {
        popularPeopleAdapter.notifyItemRangeRemoved(0, size);

    }

    @Override
    public Boolean getSearchState() {
        return isSearchAction ;
    }
}
