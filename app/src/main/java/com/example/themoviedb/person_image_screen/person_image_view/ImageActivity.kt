package com.example.themoviedb.person_image_screen.person_image_view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

import com.example.themoviedb.person_image_screen.person_image_model.PersonImageModel
import com.example.themoviedb.person_image_screen.person_image_presenter.PersonImagePresenter
import com.example.themoviedb.R
import com.squareup.picasso.Picasso

import java.io.File


class ImageActivity : AppCompatActivity(), ImageAcvtivityInterface {

    internal lateinit var personImage: ImageView
    internal lateinit var saveImageBtn: Button
    internal lateinit var photo: String
    internal lateinit var personImagePresenter: PersonImagePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        personImage = findViewById(R.id.imageid)
        saveImageBtn = findViewById(R.id.saveImageBtn)
        personImagePresenter = PersonImagePresenter(this, PersonImageModel())

        personImagePresenter.getPicturePath()


        saveImageBtn.setOnClickListener { personImagePresenter.getPermission() }
    }

    override fun getPicturePath() {
        val intent = this.intent
        photo = intent.getStringExtra("picture_path")
        this.sendPhotoStringToActivity(photo)
    }

    override fun sendPhotoStringToActivity(photo: String) {
        Picasso.with(this).load(photo)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(personImage)
    }

    override fun getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this@ImageActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    override fun savePhoto() {
        personImage.isDrawingCacheEnabled = true
        val b = personImage.drawingCache
        MediaStore.Images.Media.insertImage(contentResolver, b, photo, "")

        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(photo)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        this.setBroadcast(mediaScanIntent)
    }

    override fun setBroadcast(mediaScanIntent: Intent) {
        this.sendBroadcast(mediaScanIntent)
        Toast.makeText(this, "Photo Saved Successfully", Toast.LENGTH_LONG).show()
    }
}
