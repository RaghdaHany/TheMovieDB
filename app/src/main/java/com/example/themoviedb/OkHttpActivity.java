package com.example.themoviedb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {
    TextView okDataTextView;
    Button okHttpDataBtn;
    private ArrayList<Movie> moviesList;

    public String url = "https://api.themoviedb.org/3/movie/popular?api_key=e6f20f39139b1f5a2be132cbaaa9ce43";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        okDataTextView = (TextView) findViewById(R.id.okHttpDataTextView);
        okHttpDataBtn = (Button) findViewById(R.id.okHttpDataBtn);
        okHttpDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    run();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                 moviesList = parsigJson(myResponse);        //adding when using jsonParser
//                  moviesList = parsigWithGson(myResponse);  // adding when using gson function

                OkHttpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < moviesList.size(); i++) {
                            okDataTextView.append(moviesList.get(i).original_tiltle);
                            okDataTextView.append("\n");
                        }
//                        okDataTextView.setText(moviesList.get(0).title);    // adding when using gson function
                    }
                });

            }
        });
    }

    ArrayList<Movie> parsigJson(String movieString) {

        ArrayList<Movie> results = new ArrayList<>();
        JSONObject jsonObject = null;

        try {

            jsonObject = new JSONObject(movieString);
            JSONArray jsonArray = jsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);
                Movie movie = new Movie();

                movie.id = jsonObject.getInt("id");
                movie.original_tiltle = jsonObject.getString("original_title");
                movie.adult = jsonObject.getBoolean("adult");
                movie.popularity = jsonObject.getDouble("popularity");
                movie.vote_count = jsonObject.getDouble("vote_count");
                movie.original_language = jsonObject.getString("original_language");
//                movie.genres_id = jsonObject.getInt("genres_id");
                movie.video = jsonObject.getBoolean("video");
                movie.poster_path = jsonObject.getString("poster_path");
                movie.backdrop_path = jsonObject.getString("backdrop_path");
                movie.title = jsonObject.getString("title");
                movie.vote_average = jsonObject.getDouble("vote_average");
                movie.overview = jsonObject.getString("overview");
                movie.release_date = jsonObject.getString("release_date");

                results.add(movie);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return results;
    }


    ArrayList<Movie> parsigWithGson(String movieString) {
        Gson gson = new Gson();
        MoviesArrayList moviesArrayList = gson.fromJson(movieString,MoviesArrayList.class);
        ArrayList <Movie> movies = moviesArrayList.movieArrayList;
        return movies ;

    }
}
