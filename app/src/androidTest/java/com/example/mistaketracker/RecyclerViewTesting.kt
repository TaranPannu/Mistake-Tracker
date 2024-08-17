package com.example.mistaketracker

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4ClassRunner::class)
class RecyclerViewTesting {

        //We need to do this for the activity for which we wanna test the UI
        @get:Rule
        val activity: ActivityTestRule<*> = ActivityTestRule(MainActivity::class.java)



    //To check if recycler view displayed or not
        @Test
        fun test_appOpenedRVShown()
        {
            Espresso.onView(withId(R.id.rcy))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
//        @Test
//        fun test_appOpenedFABShown()
//        {
//            Espresso.onView(withId(R.id.float_btn))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//        }
//        @Test
//        fun test_fabClickedDialogOpened()
//        {
//            Espresso.onView(withId(R.id.float_btn)).perform(ViewActions.click()) //Verify if it is clicked
//            Espresso.onView(withId(R.id.dialog_note))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed())) //Verify if it is Opened
//
//        }

}
