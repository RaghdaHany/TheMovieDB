package com.example.themoviedb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
    public String url= "https://api.themoviedb.org/3/movie/550?api_key=e6f20f39139b1f5a2be132cbaaa9ce43";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        okDataTextView= (TextView)findViewById(R.id.okHttpDataTextView);
        okHttpDataBtn = (Button)findViewById(R.id.okHttpDataBtn);
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

                OkHttpActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        okDataTextView.setText(myResponse);
                    }
                });

            }
        });
    }

}