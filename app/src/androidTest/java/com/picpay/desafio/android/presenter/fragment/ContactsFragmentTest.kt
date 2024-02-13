package com.picpay.desafio.android.presenter.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presenter.activity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class ContactsFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_if_recyclerView_is_displayed() {
        // assert
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun test_if_searchView_is_displayed() {
        onView(withId(R.id.search)).perform(click())
        onView(withId(R.id.search)).check(matches(isDisplayed()))
    }
}