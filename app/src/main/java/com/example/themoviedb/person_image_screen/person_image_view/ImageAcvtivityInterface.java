package com.example.themoviedb.person_image_screen.person_image_view;

import android.content.Intent;

public interface ImageAcvtivityInterface {
    Intent getIntent();

    void sendPhotoStringToActivity(String photo);

    void savePhoto();

    void setBroadcast(Intent mediaScanIntent);
}
