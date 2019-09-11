package com.example.themoviedb.person_details_screen.person_details_model;

import android.os.AsyncTask;

import com.example.themoviedb.person_details_screen.person_details_controller.PersonDetailsController;

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

public class PersonDetailsModel {
    PersonDetailsController personDetailsController;

    public void setModel(PersonDetailsController personDetailsController) {
        this.personDetailsController = personDetailsController;
    }
    public PersonDetailsModel(PersonDetailsController personDetailsController) {
        this.personDetailsController = personDetailsController;
    }

    public class getPhotos extends AsyncTask<String, String, String> {

        HttpURLConnection httpURLConnection = null;
        URL url = null ;

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            BufferedReader reader = null;

            try {
                url = new URL(urls[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream stream = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (httpURLConnection != null)
                    httpURLConnection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("profiles");

                for (int i = 0; i< jsonArray.length(); i++) {

                    JSONObject profileResult = jsonArray.getJSONObject(i);
                    Profiles personDetails = new Profiles();
                    personDetails.setFile_path(profileResult.getString("file_path"));
                    personDetailsController.addPersonDetails(personDetails);
                }

                    personDetailsController.setAdapter();
//                adapter.notifyDataSetChanged();
//                recyclerView.setLayoutManager(layoutManager);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
