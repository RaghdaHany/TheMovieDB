package com.example.themoviedb.others

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView

import com.example.themoviedb.R

import java.io.InputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class LoadImage(imageView: ImageView) : AsyncTask<String, Void, Bitmap>() {

    private val imageViewReference: WeakReference<ImageView>?

    init {
        imageViewReference = WeakReference(imageView)
    }

    override fun doInBackground(vararg params: String): Bitmap? {
        try {
            return downloadBitmap(params[0])
        } catch (e: Exception) {
            // log error
        }

        return null
    }

    override fun onPostExecute(bitmap: Bitmap?) {
        var bitmap = bitmap
        if (isCancelled) {
            bitmap = null
        }

        if (imageViewReference != null) {
            val imageView = imageViewReference.get()
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap)
                } else {
                    val placeholder = imageView.context.resources.getDrawable(R.drawable.ic_launcher_background)
                    imageView.setImageDrawable(placeholder)
                }
            }
        }
    }

    private fun downloadBitmap(url: String): Bitmap? {
        var urlConnection: HttpURLConnection? = null
        try {
            val uri = URL(url)
            urlConnection = uri.openConnection() as HttpURLConnection
            val statusCode = urlConnection.responseCode
            if (statusCode != HttpURLConnection.HTTP_OK) {
                return null
            }

            val inputStream = urlConnection.inputStream
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            urlConnection!!.disconnect()
            Log.w("ImageDownloader", "Error downloading image from $url")
        } finally {
            urlConnection?.disconnect()
        }
        return null
    }
}
