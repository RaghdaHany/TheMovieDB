package com.example.themoviedb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.themoviedb.memory_cache.ImageLoader;

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

import static com.example.themoviedb.PopularPeopleActivity.CONNECTION_TIMEOUT;
import static com.example.themoviedb.PopularPeopleActivity.READ_TIMEOUT;

public class PersonDetailsActivity extends AppCompatActivity {
    PopularPeople popularPeople;
    ImageView personImage;
    TextView personName;
    TextView personDep;
    TextView personAdult;
    int id = 1;
    String idStr ;

    private RecyclerView recyclerView;
    private GridAdapter gridAdapter;
    LinearLayoutManager layoutManager;
    List<ProfilesPojo> profilesPojosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        personImage = findViewById(R.id.image_id);
        personName = findViewById(R.id.nameTextViewId);
        personDep = findViewById(R.id.departmentTextViewId);
        personAdult = findViewById(R.id.adultTextViewId);

        Intent i = getIntent();
        String name = i.getStringExtra("person_name");
        String dep = i.getStringExtra("person_department");

        boolean adult = i.getBooleanExtra("person_adult", true);
        String adultStr = new Boolean(adult).toString();

        Double pop = i.getDoubleExtra("person_popularity", 24.111);
        String popularityStr = new Double(pop).toString();

        String photo_first_path = "https://image.tmdb.org/t/p/w500/";
        String profile_path = i.getStringExtra("profile_path");
//        ImageLoader imageLoader = new ImageLoader(this);
//        imageLoader.DisplayImage(photo_first_path+profile_path , personImage);
        new LoadImage(personImage).execute(photo_first_path + profile_path);
        personName.setText(name);
        personDep.setText(dep);
        personAdult.setText("adult : " + adultStr);
        ///////////////////////////////////////////////////

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 3);
        profilesPojosList = new ArrayList<>();
        gridAdapter = new GridAdapter(PersonDetailsActivity.this, profilesPojosList);
        recyclerView.setAdapter(gridAdapter);

        id = i.getIntExtra("person_id", 1);
        idStr = new Integer(id).toString();
        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<String, String, String> {

        HttpURLConnection conn;
        URL url = null;

        @Override
        protected String doInBackground(String... strings) {
            try {
                    url = new URL("https://api.themoviedb.org/3/person/"+ id + "/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43");
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
//            pdLoading.dismiss();
//            pdLoading.dismiss();
            try {
                JSONObject obj = new JSONObject(result);
                JSONArray jArray =  obj.getJSONArray("profiles");
//                JSONObject json_Resula = result.;
//                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    ProfilesPojo profile = new ProfilesPojo();
                    profile.file_path = json_data.getString("file_path");

                    profilesPojosList.add(profile);
                }

                // Setup and Handover data to recyclerview
                gridAdapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(layoutManager);

            } catch (JSONException e) {
                Toast.makeText(PersonDetailsActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }


}