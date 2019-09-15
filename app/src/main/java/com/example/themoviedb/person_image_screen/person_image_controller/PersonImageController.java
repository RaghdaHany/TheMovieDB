package com.example.themoviedb.person_image_screen.person_image_controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.themoviedb.person_image_screen.person_image_view.ImageActivity;

import java.io.File;

public class PersonImageController {
    ImageActivity imageActivity;
    String photo ;


    public PersonImageController(ImageActivity imageActivity) {
        this.imageActivity = imageActivity;
    }

    public void getPicturePath() {
        Intent intent = imageActivity.getIntent();
        photo = intent.getStringExtra("picture_path");
        imageActivity.sendPhotoStringToActivity (photo);
    }

    public void getPermission() {
        if (ContextCompat.checkSelfPermission(imageActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions(imageActivity , new String[]
                    { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        saveImage();
    }

    private void saveImage(){
        imageActivity.savePhoto ();

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photo);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        imageActivity.sendBroadcast(mediaScanIntent);
        Toast.makeText(imageActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show();
    }
}
