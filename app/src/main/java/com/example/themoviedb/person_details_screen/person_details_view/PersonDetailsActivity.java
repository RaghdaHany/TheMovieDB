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
import com.example.themoviedb.popular_people_screen.popular_people_view.LoadImage;
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
    int id = 0 ;
    LinearLayoutManager layoutManager;
    PersonDetailsPresenter personDetailsPresenter ;
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
        personDetailsPresenter = new PersonDetailsPresenter(this , new PersonDetailsModel());
        utilities = new Utilities();

        popularPeople = personDetailsPresenter.getPersonDetails();

        Picasso.with(this).load(utilities.photo_first_path + popularPeople.getProfile_path())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(personImage);

        personName.setText(popularPeople.getName());
        personDep.setText(popularPeople.getKnown_for_department());

        String adultStr = new Boolean(popularPeople.isAdult()).toString();
        personAdult.setText("adult : " + adultStr);
        id = popularPeople.getId();

        personDetailsPresenter.fetchPersonImage("https://api.themoviedb.org/3/person/"+ id +"/images?api_key=e6f20f39139b1f5a2be132cbaaa9ce43" );
    }

    public void setImage(Profiles personImage) {
        profiles.add(personImage);
    }

    public void setImagesInAdapter() {
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(layoutManager);
    }

}