package com.example.themoviedb;

import com.example.themoviedb.popular_people_screen.popular_people_model.AsyncResponseInterface;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople;
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModelInterface;
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter;
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleViewInterface;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PopularPeoplePresenterTest {

    PopularPeoplePresenter popularPeoplePresenter;
    @Mock
    private PopularPeopleViewInterface popularPeopleViewInterface;

    @Mock
    private PopularPeopleModelInterface popularPeopleModelInterface;

    @Mock
    private AsyncResponseInterface asyncResponseInterface;
    @Before
    public void setUp (){
        MockitoAnnotations.initMocks(this);
        popularPeoplePresenter = new PopularPeoplePresenter(popularPeopleViewInterface,popularPeopleModelInterface);
    }

    @Captor
    private ArgumentCaptor<AsyncResponseInterface> asyncResponseInterfaceArgumentCaptor;

    @Test
    public void callFetchingData (){
        PopularPeople popularPeopleList =  new PopularPeople();
        popularPeoplePresenter.callFetchingData( "https://api.themoviedb.org/3/person/popular?api_key=e6f20f39139b1f5a2be132cbaaa9ce43&page=1");
    }
}
