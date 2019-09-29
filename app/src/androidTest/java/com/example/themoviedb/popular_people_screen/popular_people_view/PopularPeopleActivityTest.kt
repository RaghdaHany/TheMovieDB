package com.example.themoviedb.popular_people_screen.popular_people_view


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import kotlin.concurrent.thread


@LargeTest
@RunWith(AndroidJUnit4::class)
class PopularPeopleActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(PopularPeopleActivity::class.java)

    @Test
    fun checkDetailsScreenOpened() {
        val recyclerView = onView(withId(com.example.themoviedb.R.id.recyclerViewId))

        val textView = onView(withText("PERSON DETAILS SCREEN"))

        Thread.sleep(3000)
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        textView.check(matches(withText("PERSON DETAILS SCREEN")))
    }

    @Test
    fun ScrollingTest (){
        Thread.sleep(3000)
        tapRecyclerViewItem(com.example.themoviedb.R.id.recyclerViewId , 22)
    }

    fun tapRecyclerViewItem(recyclerViewId: Int, position: Int) {
        onView(withId(recyclerViewId)).perform(scrollToPosition<ViewHolder>(position))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
