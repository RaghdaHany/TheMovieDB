package com.example.themoviedb.person_image_screen.person_image_view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.themoviedb.popular_people_screen.LoadImage;
import com.example.themoviedb.R;
import java.io.File;


public class ImageActivity extends AppCompatActivity {

    ImageView personImage;
    Button saveImageBtn;
    String photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        personImage = findViewById(R.id.imageid);
        saveImageBtn = findViewById(R.id.saveImageBtn);

        Intent intent = getIntent();
        photo = intent.getStringExtra("picture_path");

        new LoadImage(personImage).execute(photo);
        saveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ImageActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {
                }
                else {
                    ActivityCompat.requestPermissions(ImageActivity.this, new String[]
                            { Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
                }
                saveImage();
            }
        });
    }

    private void saveImage(){
        personImage.setDrawingCacheEnabled(true);
        Bitmap b = personImage.getDrawingCache();
        MediaStore.Images.Media.insertImage(getContentResolver(), b, photo, "");

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(photo);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        Toast.makeText(ImageActivity.this, "Photo Saved Successfully", Toast.LENGTH_LONG).show();
    }
}
