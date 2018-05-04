package com.mytaxi.android_demo;

import com.mytaxi.android_demo.activities.MainActivity;

import android.content.Context;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {

    private String username;
    private String password;
    private String searchtext;
    private String drivername;

    /**
    public static ViewAction swipeRightCustom() {
        return new GeneralSwipeAction(Swipe.SLOW, GeneralLocation.CENTER_LEFT,
                GeneralLocation.CENTER_RIGHT, Press.FINGER);
    }
     */

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUserData() {
        //mActivity = mActivityRule.getActivity();
        mActivity = mActivityTestRule.getActivity();
        username = "whiteelephant261";
        password = "video1";
        searchtext = "sa";
        drivername = "Sarah Friedrich";
    }


    @Test
    public void test1_UseAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mytaxi.android_demo", appContext.getPackageName());
    }


    @Test
    public void test2_UserLogin() throws Exception{

        //Note: sleeps has been used to make the test able to be checked visually, but can be deleted

        Thread.sleep(5000);

        //Check app is in Login screen
        onView(withId(R.id.edt_username)).check(matches(isDisplayed()));
        onView(withId(R.id.edt_password)).check(matches(isDisplayed()));
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()));
        Thread.sleep(5000);


        //LOGIN:

        //Enter Username
        onView(withId(R.id.edt_username)).perform(typeText(username));
        Thread.sleep(5000);

        //Enter Password
        onView(withId(R.id.edt_password)).perform(typeText(password));
        Thread.sleep(5000);

        //Click Login button
        onView(withId(R.id.btn_login)).perform(click());
        Thread.sleep(10000);

        //Check app is in mytaxi demo screen (main screen)
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        Thread.sleep(5000);


        //CHECK USER LOGIN HAS BEEN PERFORMED CORRECTLY:

        //In order to check username in navigation drawer visually, it can be opened swiping right or clicking open drawer button
        /**
        onView(withId(R.id.drawer_layout)).perform(swipeRightCustom());
        Thread.sleep(5000);
         */
        onView(withContentDescription("Open navigation drawer")).perform(click());
        Thread.sleep(3000);

        //Check username in navigation drawer is correct
        onView(withId(R.id.nav_username)).check(matches(withText(username)));
        Thread.sleep(5000);

        //Close navigation drawer
        onView(withId(R.id.drawer_layout)).perform(swipeLeft());
        //onView(withContentDescription("Open navigation drawer")).perform(click());
        Thread.sleep(5000);

    }


    @Test
    public void test3_DriverSearch() throws InterruptedException {

        //Note: sleeps has been used to make the test able to be checked visually, but can be deleted

        Thread.sleep(5000);

        //Check app is in mytaxi demo screen (main screen)
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));


        //Enter the text to search in search input field
        onView(withId(R.id.textSearch)).perform(typeText(searchtext));
        Thread.sleep(20000);


        //Check if the desired driver is displayed in the results
        onView(withText(drivername)).inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        Thread.sleep(5000);

        //Select the desired driver
        onView(withText(drivername)).inRoot(RootMatchers.withDecorView(not(is(mActivity.getWindow().getDecorView())))).perform(click());
        Thread.sleep(5000);


        //Check app is in Driver Profile screen
        onView(withText(R.string.title_activity_driver_profile)).check(matches(isDisplayed()));

        //Check the desired driver profile screen is open
        onView(withId(R.id.textViewDriverName)).check(matches(withText(drivername)));
        Thread.sleep(5000);

        //Click the call button
        onView(withId(R.id.fab)).perform(click());
        Thread.sleep(5000);

    }

}