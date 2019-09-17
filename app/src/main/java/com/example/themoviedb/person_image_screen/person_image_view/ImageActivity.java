package com.example.themoviedb.person_image_screen.person_image_view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.themoviedb.person_image_screen.person_image_model.PersonImageModel;
import com.example.themoviedb.person_image_screen.person_image_presenter.PersonImagePresenter;
import com.example.themoviedb.R;
import com.squareup.picasso.Picasso;


public class ImageActivity extends AppCompatActivity implements ImageAcvtivityInterface{

    ImageView personImage;
    Button saveImageBtn;
    String photo;
    PersonImagePresenter personImagePresenter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        personImage = findViewById(R.id.imageid);
        saveImageBtn = findViewById(R.id.saveImageBtn);
        personImagePresenter = new PersonImagePresenter(this , new PersonImageModel());

        personImagePresenter.getPicturePath ();


        saveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personImagePresenter.getPermission ();
            }
        });
    }

    public void sendPhotoStringToActivity(String photo) {
        Picasso.with(this).load(photo)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(personImage);    }

    public void savePhoto() {
        personImage.setDrawingCacheEnabled(true);
        Bitmap b = personImage.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(), b, photo, "");
    }

    @Override
    public void setBroadcast(Intent mediaScanIntent) {
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(this, "Photo Saved Successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions(ImageActivity.this, new String[]
                    { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }
}
