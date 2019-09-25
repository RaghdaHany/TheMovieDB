package com.example.themoviedb.others

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView

import com.example.themoviedb.R
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleActivity

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    internal lateinit var viewData: TextView
    internal lateinit var fetchDataBtn: Button
    internal lateinit var okHttpBtn: Button
    internal lateinit var startAppBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewData = findViewById<View>(R.id.dataTextView) as TextView
        fetchDataBtn = findViewById<View>(R.id.fetchDataBtn) as Button
        fetchDataBtn.setOnClickListener { FetchingData().execute() }

        okHttpBtn = findViewById<View>(R.id.okHttpBtn) as Button
        okHttpBtn.setOnClickListener {
            val i = Intent(applicationContext, OkHttpActivity::class.java)
            startActivity(i)
        }

        startAppBtn = findViewById(R.id.startApp)
        startAppBtn.setOnClickListener {
            val i = Intent(this@MainActivity, PopularPeopleActivity::class.java)
            startActivity(i)
        }
    }


    private inner class FetchingData : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg params: Void): String? {

            var urlConnection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            var jsonResponse: String? = null

            try {
                val url = URL("https://api.themoviedb.org/3/movie/550?api_key=e6f20f39139b1f5a2be132cbaaa9ce43")

                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val buffer = StringBuffer()

                if (inputStream == null) {
                    return null
                }

                reader = BufferedReader(InputStreamReader(inputStream))

                var line: String
                line = reader.readLine()
                    buffer.append(line + "\n")


                if (buffer.length == 0) {
                    return null
                }

                jsonResponse = buffer.toString()
                return jsonResponse

            } catch (e: IOException) {
                e.printStackTrace()
                return null

            } finally {
                urlConnection?.disconnect()
                if (reader != null) {
                    try {
                        reader.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            viewData.text = s
            Log.i("json", s)
        }
    }
}
