package com.example.themoviedb.person_image_screen.person_image_presenter;

import android.content.Intent;
import android.net.Uri;
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
        imageAcvtivityInterface.getPermission ();
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
