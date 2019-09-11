package com.example.themoviedb.popular_people_screen.popular_people_model;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.themoviedb.popular_people_screen.popular_people_controller.PopularPeopleController;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleActivity;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleAdapter;

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
import java.util.List;

public class AsyncFetch extends AsyncTask<String, String, String> {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    List<PopularPeople> popularPeopleList ;
    private RecyclerView recyclerView;
    private PopularPeopleAdapter popularPeopleAdapter;
    PopularPeopleController popularPeopleController ;

    public void setModel(PopularPeopleController popularPeopleController) {
        this.popularPeopleController = popularPeopleController;
    }
    public AsyncFetch(PopularPeopleController popularPeopleController) {
        this.popularPeopleController = popularPeopleController;
    }

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

                popularPeopleController.addingPerson(person);
            }

               popularPeopleController.settingAdapter ();
//            // Setup and Handover data to recyclerview
//            popularPeopleAdapter.notifyDataSetChanged();
//            recyclerView.setLayoutManager(layoutManager);

        } catch (JSONException e) {
//            Toast.makeText(PopularPeopleActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    public void startFetching(String s) {

        new AsyncFetch(popularPeopleController).execute(s);
    }
}