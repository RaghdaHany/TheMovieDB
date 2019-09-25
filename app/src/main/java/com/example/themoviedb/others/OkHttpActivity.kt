package com.example.themoviedb.others

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.themoviedb.R
import com.google.gson.Gson

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.util.ArrayList

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class OkHttpActivity : AppCompatActivity() {
    internal lateinit var okDataTextView: TextView
    internal lateinit var okHttpDataBtn: Button
    private var moviesList: ArrayList<Movie>? = null

    var url = "https://api.themoviedb.org/3/movie/popular?api_key=e6f20f39139b1f5a2be132cbaaa9ce43"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ok_http)

        okDataTextView = findViewById<View>(R.id.okHttpDataTextView) as TextView
        okHttpDataBtn = findViewById<View>(R.id.okHttpDataBtn) as Button
        okHttpDataBtn.setOnClickListener {
            try {
                run()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    @Throws(IOException::class)
    internal fun run() {

        val client = OkHttpClient()

        val request = Request.Builder()
                .url(url)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

                val myResponse = response.body().string()
                moviesList = parsigJson(myResponse)        //adding when using jsonParser
                //                  moviesList = parsigWithGson(myResponse);  // adding when using gson function

                this@OkHttpActivity.runOnUiThread {
                    for (i in moviesList!!.indices) {
                        okDataTextView.append(moviesList!![i].original_tiltle)
                        okDataTextView.append("\n")
                    }
                    //                        okDataTextView.setText(moviesList.get(0).title);    // adding when using gson function
                }

            }
        })
    }

    internal fun parsigJson(movieString: String): ArrayList<Movie> {

        val results = ArrayList<Movie>()
        var jsonObject: JSONObject? = null

        try {

            jsonObject = JSONObject(movieString)
            val jsonArray = jsonObject.getJSONArray("results")

            for (i in 0 until jsonArray.length()) {

                jsonObject = jsonArray.getJSONObject(i)
                val movie = Movie()

                movie.id = jsonObject!!.getInt("id")
                movie.original_tiltle = jsonObject.getString("original_title")
                movie.adult = jsonObject.getBoolean("adult")
                movie.popularity = jsonObject.getDouble("popularity")
                movie.vote_count = jsonObject.getDouble("vote_count")
                movie.original_language = jsonObject.getString("original_language")
                //                movie.genres_id = jsonObject.getInt("genres_id");
                movie.video = jsonObject.getBoolean("video")
                movie.poster_path = jsonObject.getString("poster_path")
                movie.backdrop_path = jsonObject.getString("backdrop_path")
                movie.title = jsonObject.getString("title")
                movie.vote_average = jsonObject.getDouble("vote_average")
                movie.overview = jsonObject.getString("overview")
                movie.release_date = jsonObject.getString("release_date")

                results.add(movie)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return results
    }


    internal fun parsigWithGson(movieString: String): ArrayList<Movie>? {
        val gson = Gson()
        val moviesArrayList = gson.fromJson(movieString, MoviesArrayList::class.java)
        return moviesArrayList.movieArrayList

    }
}
