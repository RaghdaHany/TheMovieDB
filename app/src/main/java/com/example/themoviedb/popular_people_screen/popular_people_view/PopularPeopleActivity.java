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

public class PopularPeopleActivity extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener{

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    Boolean isScrolling = false ;
    int currentItems , totalItems , scrollingOutItems ;
    public int  page = 1 ;
    private RecyclerView recyclerView;
    private PopularPeopleAdapter popularPeopleAdapter;
    LinearLayoutManager layoutManager ;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<PopularPeople> popularPeopleList ;
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

//        isSearchAction = false;
        new AsyncFetch().execute(data_url);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                popularPeopleList.clear();
                popularPeopleAdapter.notifyDataSetChanged();
//                isSearchAction = true;
                if (isSearchAction) {
                    searchPage=1;
                    searchPageStr = String.valueOf(searchPage);
                    new AsyncFetch().execute(search_url+searchstr+"&page="+searchPageStr);
                }
                else {
                    page=1;
                    pageStr = String.valueOf(page);
                    new AsyncFetch().execute(data_url+pageStr);
                }
                recyclerView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
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
                    if (isSearchAction) {
                        searchPage = searchPage + 1;
                        searchPageStr = String.valueOf(searchPage);
                        new AsyncFetch().execute(search_url+searchstr+"&page="+searchPageStr);
                    }
                    else {
                        page = page + 1;
                        pageStr = String.valueOf(page);
                        new AsyncFetch().execute(data_url+pageStr);
                    }
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
    public boolean onQueryTextSubmit(String s) {
        searchstr = s;
        if(!s.equals("")) {
            isSearchAction = true;
            popularPeopleList.clear();
            new AsyncFetch().execute(search_url + searchstr);
        }
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (searchView.getQuery().length() == 0) {
            isSearchAction = false;
            popularPeopleList.clear();
            searchView.clearFocus();
            new AsyncFetch().execute(data_url);
        }
        return true;
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {

//        ProgressDialog pdLoading = new ProgressDialog(PopularPeopleActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                        url = new URL(params[0]);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");
                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }

            try {

            int response_code = conn.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {

                // Read data sent from server
                InputStream input = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                // Pass data to onPostExecute method
                return (result.toString());

            } else {

                return ("unsuccessful");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } finally {
                conn.disconnect();
            }
    }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject obj = new JSONObject(result);
                JSONArray jArray =  obj.getJSONArray("results");

                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    PopularPeople person = new PopularPeople();

                    person.setName(json_data.getString("name"));
                    person.setKnown_for_department(json_data.getString("known_for_department"));
                    person.setProfile_path(json_data.getString("profile_path"));
                    person.setId(json_data.getInt("id"));

                    popularPeopleList.add(person);
                }

                // Setup and Handover data to recyclerview
                popularPeopleAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(layoutManager);

            } catch (JSONException e) {
                Toast.makeText(PopularPeopleActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }


}
