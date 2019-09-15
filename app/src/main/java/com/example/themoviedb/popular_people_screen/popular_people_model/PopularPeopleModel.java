package com.example.themoviedb.popular_people_screen.popular_people_model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.themoviedb.R;
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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PopularPeopleModel {
    private static final int CONNECTION_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 15000;
    List<PopularPeople> popularPeopleList;
    private RecyclerView recyclerView;
    private PopularPeopleAdapter popularPeopleAdapter;
    PopularPeopleController popularPeopleController;

    public void setModel(PopularPeopleController popularPeopleController) {
        this.popularPeopleController = popularPeopleController;
    }

    public PopularPeopleModel(PopularPeopleController popularPeopleController) {
        this.popularPeopleController = popularPeopleController;
    }

    public class AsyncFetch extends AsyncTask<String, String, String> {

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

                    popularPeopleController.addingPerson(person);
                }

                popularPeopleController.settingAdapter();
//            // Setup and Handover data to recyclerview
//            popularPeopleAdapter.notifyDataSetChanged();
//            recyclerView.setLayoutManager(layoutManager);

            } catch (JSONException e) {
//            Toast.makeText(PopularPeopleActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }

        }
    }

    public void startFetching(String s) {

        new AsyncFetch().execute(s);
    }

    public class ImageLoaderModel extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;

        public ImageLoaderModel(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return downloadBitmap(params[0]);
            } catch (Exception e) {
                // log error
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher_background);
                        imageView.setImageDrawable(placeholder);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();
                int statusCode = urlConnection.getResponseCode();
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return null;
                }

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}