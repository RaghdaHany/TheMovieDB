package com.example.themoviedb.person_image_screen.person_image_model;

import com.example.themoviedb.person_image_screen.person_image_presenter.PersonImagePresenter;

public class PersonImageModel implements PersonImageModelInterface {
    PersonImagePresenter personImagePresenter ;
    @Override
    public void setModel(PersonImagePresenter personImagePresenter) {
        this.personImagePresenter = personImagePresenter;

    }
}
