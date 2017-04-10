package com.warp.sample;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(withId(R.id.etUsername));
        appCompatEditText.perform(scrollTo(), replaceText("sand"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(withId(R.id.etPassword));
        appCompatEditText2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(withId(R.id.etPassword));
        appCompatEditText3.perform(scrollTo(), replaceText("passwor"), closeSoftKeyboard());

    }

}
