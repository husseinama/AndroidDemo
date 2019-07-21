package com.freenow.android_demo;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.freenow.android_demo.activities.MainActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest extends Intent {

    //App Activity
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    //Grant permission for location access
    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION");

    ViewInteraction userName = onView(withId(R.id.edt_username));
    ViewInteraction password = onView(withId(R.id.edt_password));

    //Login Function
    public void logIn() {

        userName.perform(typeText("crazydog335"), closeSoftKeyboard());

        password.perform(typeText("venture"), closeSoftKeyboard());

        ViewInteraction loginBTN = onView(withId(R.id.btn_login));
        loginBTN.perform(click());
    }

    // APP Initializing
    @Before
    public void setUp() throws Exception {
        mActivityTestRule.launchActivity(this);
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.freenow.android_demo", appContext.getPackageName());
    }

    //Login TC Positive scenario
    @Test
    public void logInTC() throws Exception
    {

        logIn();
        Thread.sleep(3000);

        onView(withId(R.id.textSearch)).check(matches(isDisplayed()));

    }

    //Search TC Positive scenario
    @Test
    public void searchTC() throws Exception
    {
        logIn();
        ViewInteraction search = onView(withId(R.id.textSearch));
        search.perform(click());
        search.perform(typeText("sa"));
        Thread.sleep(3000);
        onView(withText("Sarah Scott")).inRoot(RootMatchers.isPlatformPopup()).perform(click());
//        onData(anything()).inAdapterView(withId(android.R.id.text1));
        Thread.sleep(3000);
        onView(withId(R.id.textViewDriverLocation)).check(matches(withText("6834 charles st")));
        onView(isRoot()).perform(ViewActions.pressBack());

    }

    // Closing test
    @After
    public void tearDown () throws Exception {

        // Open Drawer
        onView(withId(R.id.drawer_layout))
                .perform(DrawerActions.open());
        // Press Logout
        onView(withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_logout));

    }

}
