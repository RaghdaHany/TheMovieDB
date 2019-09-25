package com.example.themoviedb

import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModelInterface
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleViewInterface
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PopularPeoplePresenterTest {
    internal var popularPeoplePresenter: PopularPeoplePresenter? = null

    @Mock
    internal lateinit var popularPeopleViewInterface : PopularPeopleViewInterface

    @Mock
    internal lateinit var popularPeopleModelInterface: PopularPeopleModelInterface

    @Before
    fun setUp ()  {
        MockitoAnnotations.initMocks(this)
        popularPeoplePresenter = PopularPeoplePresenter(popularPeopleViewInterface,popularPeopleModelInterface)
    }

    @Test
    fun getFetchedDataTest (){
        val url = "https://api.themoviedb.org/3/person/popular?api_key=e6f20f39139b1f5a2be132cbaaa9ce43&page=1"
        popularPeoplePresenter?.callFetchingData(url)

        val callback = argumentCaptor<(PopularPeople?) -> Unit>()
        val popularPeople = PopularPeople()
        Mockito.`when`(popularPeoplePresenter?.callFetchingData(url))
                .then {
                    callback.firstValue.invoke(popularPeople)
                }
        verify(popularPeopleViewInterface).settingAdapterInList()
    }




}
