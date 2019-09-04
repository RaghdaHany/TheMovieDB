package com.example.themoviedb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.util.concurrent.ExecutionException;

import static com.example.themoviedb.PopularPeopleActivity.CONNECTION_TIMEOUT;
import static com.example.themoviedb.PopularPeopleActivity.READ_TIMEOUT;

public class PersonDetailsActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    PopularPeople popularPeople;
    ImageView personImage;
    TextView personName;
    TextView personDep;
    TextView personAdult;
    private RecyclerView recyclerView;
    ArrayList<Profiles> profiles ;
    GridAdapter adapter ;
    int id ;

    LinearLayoutManager layoutManager;
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
        id = i.getIntExtra("id", 1);

        boolean adult = i.getBooleanExtra("person_adult", true);
        String adultStr = new Boolean(adult).toString();

        String photo_first_path = "https://image.tmdb.org/t/p/w500/";
        String profile_path = i.getStringExtra("profile_path");
        new LoadImage(personImage).execute(photo_first_path + profile_path);

        personName.setText(name);
        personDep.setText(dep);
        personAdult.setText("adult : " + adultStr);

        Intent intent = getIntent();
        String profile = intent.getStringExtra("profile_path");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this ,3 );
        profiles = new ArrayList<>();
        adapter = new GridAdapter(profiles, PersonDetailsActivity.this);
        recyclerView.setAdapter(adapter);

//        personImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PersonDetailsActivity.this, ImageActivity.class);
//                Bundle arg = new Bundle();
//                arg.putString("picture_path", popprofile);
//                intent.putExtra("data", arg);
//                startActivity(intent);
//            }
//        });

        new getPhotos().execute("https://api.themoviedb.org/3/person/" + id + "/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43");
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
                    Profiles personProfile = new Profiles();
                    personProfile.setFile_path(profileResult.getString("file_path"));
                    profiles.add(personProfile);
                }

                adapter.notifyDataSetChanged();
                recyclerView.setLayoutManager(layoutManager);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}