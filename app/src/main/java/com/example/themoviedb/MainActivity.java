package com.example.themoviedb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView viewData;
    Button fetchDataBtn;
    Button okHttpBtn ;
    Button startAppBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewData = (TextView) findViewById(R.id.dataTextView);
        fetchDataBtn = (Button) findViewById(R.id.fetchDataBtn);
        fetchDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchingData().execute();
            }
        });

        okHttpBtn = (Button) findViewById(R.id.okHttpBtn);
        okHttpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),OkHttpActivity.class);
                startActivity(i);
            }
        });

        startAppBtn = findViewById(R.id.startApp);
        startAppBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , PopularPeopleActivity.class);
                startActivity(i);
            }
        });
    }


    private class FetchingData extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String jsonResponse = null;

            try {
                URL url = new URL("https://api.themoviedb.org/3/movie/550?api_key=e6f20f39139b1f5a2be132cbaaa9ce43");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                jsonResponse = buffer.toString();
                return jsonResponse;

            } catch (IOException e) {
                e.printStackTrace();
                return null;

            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            viewData.setText(s);
            Log.i("json", s);
        }
    }
}
