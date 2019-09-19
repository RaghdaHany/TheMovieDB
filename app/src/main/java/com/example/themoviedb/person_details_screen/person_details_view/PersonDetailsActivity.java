package com.example.themoviedb.person_details_screen.person_details_view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.themoviedb.others.Utilities;
import com.example.themoviedb.person_details_screen.person_details_model.PersonDetailsModel;
import com.example.themoviedb.person_details_screen.person_details_presenter.PersonDetailsPresenter;
import com.example.themoviedb.person_details_screen.person_details_model.Profiles;
import com.example.themoviedb.R;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonDetailsActivity extends AppCompatActivity implements PersonDetailsViewInterface{

    PopularPeople popularPeople;
    ImageView personImage;
    TextView personName;
    TextView personDep;
    TextView personAdult;
    private RecyclerView recyclerView;
    ArrayList<Profiles> profiles ;
    GridAdapter adapter ;
    LinearLayoutManager layoutManager;
    PersonDetailsPresenter personDetailsPresenter ;
    Utilities utilities ;
    String name ;
    String dep ;
    int id = 0;
    Boolean adult ;
    String profile_path ;

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
        personDetailsPresenter = new PersonDetailsPresenter(this , new PersonDetailsModel());
        utilities = new Utilities();
        popularPeople = new PopularPeople();

        personDetailsPresenter.getPersonDetails();

        Picasso.with(this).load(utilities.photo_first_path + popularPeople.getProfile_path())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(personImage);

        personName.setText(name);
        personDep.setText(dep);

        String adultStr = new Boolean(adult).toString();
        personAdult.setText("adult : " + adultStr);

        personDetailsPresenter.fetchPersonImage("https://api.themoviedb.org/3/person/"+ id +"/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43" );
    }

    public void setImage(Profiles personImage) {
        profiles.add(personImage);
    }

    public void setImagesInAdapter() {
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void getPersonDetails() {
        Intent i = this.getIntent();
        Bundle extras = i.getExtras();

         name = extras.getString("person_name");
         dep = extras.getString("person_department");
         id = extras.getInt("person_id", 1);
         adult = extras.getBoolean("person_adult", true);
         profile_path = extras.getString("profile_path");

        popularPeople.setName(name);
        popularPeople.setId(id);
        popularPeople.setKnown_for_department(dep);
        popularPeople.setAdult(adult);
        popularPeople.setProfile_path(profile_path);
    }

}