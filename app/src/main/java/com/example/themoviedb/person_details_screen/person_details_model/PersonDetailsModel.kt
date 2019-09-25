package com.example.themoviedb.person_details_screen.person_details_model

import android.os.AsyncTask

import com.example.themoviedb.person_details_screen.person_details_presenter.PersonDetailsPresenter
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

class PersonDetailsModel : PersonDetailsModelInterface {

    //    PersonDetailsPresenter personDetailsPresenter;
    //
    //    @Override
    //    public void setModel(PersonDetailsPresenter personDetailsPresenter) {
    //        this.personDetailsPresenter = personDetailsPresenter;
    //
    //    }

    class GetPhotos(asyncResponse: AsyncFetchinImagesInterface) : AsyncTask<String, String, String>() {

        var asyncFetchinImagesInterface: AsyncFetchinImagesInterface? = null//Call back interface

        internal var httpURLConnection: HttpURLConnection? = null
        internal var url: URL? = null

        init {
            asyncFetchinImagesInterface = asyncResponse       //Assigning call back interface through constructor
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg urls: String): String? {
            var reader: BufferedReader? = null

            try {
                url = URL(urls[0])
                httpURLConnection = url!!.openConnection() as HttpURLConnection
                httpURLConnection!!.connect()

                val stream = httpURLConnection!!.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()

                var line = ""
                line = reader.readLine()
                    buffer.append(line)

                return buffer.toString()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (httpURLConnection != null)
                    httpURLConnection!!.disconnect()
                try {
                    reader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return null
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            try {

                val jsonObject = JSONObject(result)
                val jsonArray = jsonObject.getJSONArray("profiles")

                for (i in 0 until jsonArray.length()) {

                    val profileResult = jsonArray.getJSONObject(i)
                    val personImage = Profiles()
                    personImage.file_path = profileResult.getString("file_path")
                    asyncFetchinImagesInterface!!.processFinish(personImage)
                    //                    personDetailsPresenter.addPersonImages(personImage);
                }

                //                personDetailsPresenter.setAdapter();

            } catch (e: JSONException) {
                e.printStackTrace()
            }

        }
    }
}
