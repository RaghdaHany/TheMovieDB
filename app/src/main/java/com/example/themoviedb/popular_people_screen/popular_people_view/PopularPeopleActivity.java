package com.example.themoviedb.popular_people_screen.popular_people_view;

import android.os.AsyncTask;
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
import android.widget.Toast;

import com.example.themoviedb.R;
import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.popular_people_screen.popular_people_controller.PopularPeopleController;
import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncFetch;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PopularPeopleActivity extends AppCompatActivity
//        implements android.widget.SearchView.OnQueryTextListener
{

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

    String search_url;
    String data_url;
    private Boolean isSearchAction= false;
    private String searchstr = "";
    private String pageStr="";
    MenuItem menuItem;

    int searchPage = 1;
    String searchPageStr="";
    PopularPeopleController popularPeopleController ;
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

        pageStr = String.valueOf(page);
        data_url = "https://api.themoviedb.org/3/person/popular?api_key=e6f20f39139b1f5a2be132cbaaa9ce43"+"&"+"page=";
        search_url = "https://api.themoviedb.org/3/search/person?api_key=e6f20f39139b1f5a2be132cbaaa9ce43&query=";

        utilities = new Utilities();
        pageStr = String.valueOf(page);
        popularPeopleController = new PopularPeopleController(this);
        popularPeopleController.callFetchingData (utilities.popularPeopleURL+pageStr);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
              popularPeopleController.callSwipeFun ();
              mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////
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
                    popularPeopleController.callScrollingFun();
//                    if (isSearchAction) {
//                        searchPage = searchPage + 1;
//                        searchPageStr = String.valueOf(searchPage);
//                        new AsyncFetch().execute(search_url+searchstr+"&page="+searchPageStr);
//                    }
//                    else {
//                        page = page + 1;
//                        pageStr = String.valueOf(page);
//                        new AsyncFetch().execute(data_url+pageStr);
//                    }
                }
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.my_menu, menu);
//        menuItem = menu.findItem(R.id.search_btn);
//        searchView = (android.widget.SearchView) menuItem.getActionView();
//        searchView.setOnQueryTextListener(this);
//        searchView.clearFocus();
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String s) {
//        searchstr = s;
//        if(!s.equals("")) {
//            int size = popularPeopleList.size();
//            if (size > 0) {
//                for (int i = 0; i < size; i++) {
//                    popularPeopleList.remove(0);
//                }
//                popularPeopleAdapter.notifyItemRangeRemoved(0, size);
//            }
//            isSearchAction = true;
//            searchPage = 1 ;
//            searchPageStr = String.valueOf(searchPage);
//            new AsyncFetch().execute(search_url + searchstr + "&page="+searchPageStr);
//        }
//        searchView.clearFocus();
//        return true;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String s) {
//        if (searchView.getQuery().length() == 0) {
//            isSearchAction = false;
//            popularPeopleList.clear();
//            searchView.clearFocus();
//            new AsyncFetch().execute(data_url);
//        }
//        return true;
//    }
//
    public void setPerson(PopularPeople person) {
        popularPeopleList.add(person);
    }

    public void settingAdapterInList() {
        popularPeopleAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }

    public void clearList() {
        int size = popularPeopleList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                popularPeopleList.remove(0);
            }
            popularPeopleAdapter.notifyItemRangeRemoved(0, size);
        }
    }
}
