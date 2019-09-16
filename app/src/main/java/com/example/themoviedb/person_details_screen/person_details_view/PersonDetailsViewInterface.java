package com.example.themoviedb.person_details_screen.person_details_view;

import android.content.Intent;

import com.example.themoviedb.person_details_screen.person_details_model.Profiles;

public interface PersonDetailsViewInterface {
    void setImage(Profiles personImage);

    void setImagesInAdapter();

    Intent getIntent();
}
