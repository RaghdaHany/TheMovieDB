package com.example.themoviedb.popular_people_screen.popular_people_model

import android.os.AsyncTask
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class PopularPeopleModel : PopularPeopleModelInterface {

    class AsyncFetch(asyncResponseInterface: AsyncResponseInterface) : AsyncTask<String, String, String>() {

        var asyncResponse: AsyncResponseInterface? = null   //Call back interface

        internal lateinit var conn: HttpURLConnection
        internal var url: URL? = null

        init {
            asyncResponse = asyncResponseInterface        //Assigning call back interface through constructor
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String): String {
            try {
                url = URL(params[0])

            } catch (e: MalformedURLException) {
                e.printStackTrace()
                return e.toString()
            }

            try {
                conn = url!!.openConnection() as HttpURLConnection
                conn.readTimeout = READ_TIMEOUT
                conn.connectTimeout = CONNECTION_TIMEOUT
                conn.requestMethod = "GET"
                conn.doOutput = true

            } catch (e1: IOException) {
                e1.printStackTrace()
                return e1.toString()
            }

            try {

                val response_code = conn.responseCode
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    val input = conn.inputStream
                    val reader = BufferedReader(InputStreamReader(input))
                    val result = StringBuilder()
                    var line: String

                        line = reader.readLine()
                        result.append(line)


                    // Pass data to onPostExecute method
                    return result.toString()

                } else {

                    return "unsuccessful"
                }

            } catch (e: IOException) {
                e.printStackTrace()
                return e.toString()
            } finally {
                conn.disconnect()
            }
        }

        override fun onPostExecute(result: String) {

            try {
                val obj = JSONObject(result)
                val jArray = obj.getJSONArray("results")

                for (i in 0 until jArray.length()) {
                    val json_data = jArray.getJSONObject(i)
                    val person = PopularPeople()

                    person.name = (json_data.getString("name"))
                    person.known_for_department =(json_data.getString("known_for_department"))
                    person.profile_path =(json_data.getString("profile_path"))
                    person.id =(json_data.getInt("id"))

                    asyncResponse!!.processFinish(person)
                }

            } catch (e: JSONException) {
            }

        }
    }

    companion object {
        private val CONNECTION_TIMEOUT = 10000
        private val READ_TIMEOUT = 15000
    }
}