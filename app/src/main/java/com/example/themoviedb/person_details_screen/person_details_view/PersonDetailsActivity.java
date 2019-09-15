package com.example.themoviedb.person_details_screen.person_details_view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.person_details_screen.person_details_controller.PersonDetailsController;
import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.popular_people_screen.LoadImage;
import com.example.themoviedb.R;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;

import java.util.ArrayList;

public class PersonDetailsActivity extends AppCompatActivity {

    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    PopularPeople popularPeople;
    ImageView personImage;
    TextView personName;
    TextView personDep;
    TextView personAdult;
    private RecyclerView recyclerView;
    ArrayList<Profiles> profiles ;
    GridAdapter adapter ;
    int id = 0 ;
    LinearLayoutManager layoutManager;
    PersonDetailsController personDetailsController ;
    Utilities utilities ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);

        personImage = findViewById(R.id.image_id);
        personName = findViewById(R.id.nameTextViewId);
        personDep = findViewById(R.id.departmentTextViewId);
        personAdult = findViewById(R.id.adultTextViewId);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this ,3 );
        profiles = new ArrayList<>();
        adapter = new GridAdapter(profiles, PersonDetailsActivity.this);
        recyclerView.setAdapter(adapter);
        personDetailsController = new PersonDetailsController(this );
        utilities = new Utilities();

        popularPeople = personDetailsController.getPersonDetails();
        String adultStr = new Boolean(popularPeople.isAdult()).toString();

        new LoadImage(personImage).execute(utilities.photo_first_path + popularPeople.getProfile_path());
        personName.setText(popularPeople.getName());
        personDep.setText(popularPeople.getKnown_for_department());
        personAdult.setText("adult : " + adultStr);
        id = popularPeople.getId();
//        Intent intent = getIntent();
//        String profile = intent.getStringExtra("profile_path");

        personDetailsController.fetchPersonImage("https://api.themoviedb.org/3/person/"+ id +"/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43" );
//        new getPhotos().execute("https://api.themoviedb.org/3/person/" + id + "/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43");
    }


    public void setImage(Profiles personImage)
    {
        profiles.add(personImage);
    }

    public void setImagesInAdapter() {
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }
}