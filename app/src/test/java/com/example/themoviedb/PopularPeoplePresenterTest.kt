package com.example.themoviedb

import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeople
import com.example.themoviedb.popular_people_screen.popular_people_model.PopularPeopleModelInterface
import com.example.themoviedb.popular_people_screen.popular_people_presenter.PopularPeoplePresenter
import com.example.themoviedb.popular_people_screen.popular_people_view.PopularPeopleViewInterface
import com.nhaarman.mockito_kotlin.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.lang.AssertionError

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
    fun callScrollFunctionTest() {
        val expectedPageNumber = 2

        popularPeoplePresenter?.callScrollingFun()

        whenever(popularPeoplePresenter?.searchState != true )

        assertEquals(expectedPageNumber, popularPeoplePresenter?.page)
    }

    @Test
    fun callSearchScrollFunctionTest() {
        val expectedPageNumber = 2

        popularPeoplePresenter?.callScrollingFun()

        whenever(popularPeoplePresenter?.searchState == true )

        assertEquals(expectedPageNumber,  popularPeoplePresenter?.page)
    }

    @Test
    fun clearListTest() {
        popularPeoplePresenter?.callFetchingData()
        var size = popularPeopleViewInterface.getList()?.size
         size = 20
        val expectedSize = 0
        var sizeAfterClear :Int

        if (size != null) {
            if (size > 0){
                popularPeoplePresenter?.clearList()
            }

        }
        sizeAfterClear = popularPeopleViewInterface.getList()?.size!!
        assertEquals(expectedSize, sizeAfterClear)
    }

//    @Test
//    fun callSwipeFunctionTest() {
//
//        val expectedPageNumber = 1
//        var pageNumber = popularPeoplePresenter?.page
//        pageNumber = 2
//
////        popularPeoplePresenter?.callFetchingData()
//        popularPeoplePresenter?.callSwipeFun()
//
////        this.clearListTest()
//
////        whenever(popularPeoplePresenter?.searchState != true ).thenReturn(true)
//            assertEquals(expectedPageNumber, popularPeoplePresenter?.page)
//    }
}
