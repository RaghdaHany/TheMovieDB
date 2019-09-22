package com.example.themoviedb.popular_people_screen.popular_people_model;

import android.os.AsyncTask;
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter;

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

public class PopularPeopleModel implements PopularPeopleModelInterface {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;

    public static class AsyncFetch extends AsyncTask<String, String, String> {

        public AsyncResponseInterface asyncResponse = null;   //Call back interface

        public AsyncFetch(AsyncResponseInterface asyncResponseInterface) {
            asyncResponse = asyncResponseInterface;        //Assigning call back interface through constructor
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
                JSONArray jArray = obj.getJSONArray("results");

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    PopularPeople person = new PopularPeople();

                    person.setName(json_data.getString("name"));
                    person.setKnown_for_department(json_data.getString("known_for_department"));
                    person.setProfile_path(json_data.getString("profile_path"));
                    person.setId(json_data.getInt("id"));

                    asyncResponse.processFinish(person);
                }

            } catch (JSONException e) {
            }

        }
    }
}