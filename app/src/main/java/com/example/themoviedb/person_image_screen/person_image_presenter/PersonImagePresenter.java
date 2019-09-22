package com.example.themoviedb.person_image_screen.person_image_presenter;

import com.example.themoviedb.person_image_screen.person_image_model.PersonImageModelInterface;
import com.example.themoviedb.person_image_screen.person_image_view.ImageAcvtivityInterface;

public class PersonImagePresenter {
    ImageAcvtivityInterface imageAcvtivityInterface ;
    PersonImageModelInterface personImageModelInterface ;

    public PersonImagePresenter(ImageAcvtivityInterface imageAcvtivityInterface, PersonImageModelInterface personImageModelInterface) {
        this.imageAcvtivityInterface = imageAcvtivityInterface;
        this.personImageModelInterface = personImageModelInterface;
    }

    public void getPicturePath() {
        imageAcvtivityInterface.getPicturePath();

    }

    public void getPermission() {
        imageAcvtivityInterface.getPermission ();
        saveImage();
    }

    private void saveImage(){
        imageAcvtivityInterface.savePhoto ();
    }
}
