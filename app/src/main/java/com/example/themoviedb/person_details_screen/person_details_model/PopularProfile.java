package com.example.themoviedb.person_details_screen.person_details_model;

import java.util.ArrayList;

public class PopularProfile {
    private ArrayList<Profiles> pictures;
    private int id;

    public ArrayList<Profiles> getPictures() {
        return pictures;
    }

    public void setPictures(ArrayList<Profiles> pictures) {
        this.pictures = pictures;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}