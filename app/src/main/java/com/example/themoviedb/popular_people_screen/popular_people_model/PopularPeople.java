package com.example.themoviedb.popular_people_screen.popular_people_model;

import java.util.ArrayList;

public class PopularPeople {

    Double popularity;
    String known_for_department;
    int gender ;
    int id ;
    String profile_path;
    boolean adult ;
    String name ;
    ArrayList<PeopleKnownForPojo> known_for ;

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public void setKnown_for_department(String known_for_department) {
        this.known_for_department = known_for_department;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKnown_for(ArrayList<PeopleKnownForPojo> known_for) {
        this.known_for = known_for;
    }

    public Double getPopularity() {
        return popularity;
    }

    public String getKnown_for_department() {
        return known_for_department;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getName() {
        return name;
    }

    public ArrayList<PeopleKnownForPojo> getKnown_for() {
        return known_for;
    }
}
