package com.example.themoviedb.person_image_screen.person_image_presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.themoviedb.person_image_screen.person_image_model.PersonImageModelInterface;
import com.example.themoviedb.person_image_screen.person_image_view.ImageAcvtivityInterface;

import java.io.File;

public class PersonImagePresenter {
    ImageAcvtivityInterface imageAcvtivityInterface ;
    PersonImageModelInterface personImageModelInterface ;
    String photo ;


    public PersonImagePresenter(ImageAcvtivityInterface imageAcvtivityInterface, PersonImageModelInterface personImageModelInterface) {
        this.imageAcvtivityInterface = imageAcvtivityInterface;
        this.personImageModelInterface = personImageModelInterface;
        personImageModelInterface.setModel(this);

    }

    public void getPicturePath() {
        Intent intent = imageAcvtivityInterface.getIntent();
        photo = intent.getStringExtra("picture_path");
        imageAcvtivityInterface.sendPhotoStringToActivity (photo);
    }

    public void getPermission() {
        if (ContextCompat.checkSelfPermission((Context) imageAcvtivityInterface, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
        }
        else {
            ActivityCompat.requestPermissions((Activity) imageAcvtivityInterface, new String[]
                    { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
        saveImage();
    }

    private void saveImage(){
        imageAcvtivityInterface.savePhoto ();

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photo);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        imageAcvtivityInterface.setBroadcast (mediaScanIntent);

    }
}
