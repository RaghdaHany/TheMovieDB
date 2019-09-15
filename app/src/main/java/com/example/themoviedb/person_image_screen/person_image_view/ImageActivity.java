package com.example.themoviedb.person_image_screen.person_image_view;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.themoviedb.person_image_screen.person_image_controller.PersonImageController;
import com.example.themoviedb.R;
import com.example.themoviedb.popular_people_screen.popular_people_view.LoadImage;


public class ImageActivity extends AppCompatActivity {

    ImageView personImage;
    Button saveImageBtn;
    String photo;
    PersonImageController personImageController ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        personImage = findViewById(R.id.imageid);
        saveImageBtn = findViewById(R.id.saveImageBtn);
        personImageController = new PersonImageController(this );

        personImageController.getPicturePath ();


        saveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personImageController.getPermission ();
            }
        });
    }

    public void sendPhotoStringToActivity(String photo) {
        new LoadImage(personImage).execute(photo);
    }

    public void savePhoto() {
        personImage.setDrawingCacheEnabled(true);
        Bitmap b = personImage.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(), b, photo, "");
    }
}
