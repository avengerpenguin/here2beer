package uk.co.rossfenning.android.here2beer.test;

import android.app.Instrumentation.ActivityMonitor;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Condition;
import com.jayway.android.robotium.solo.Solo;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.rossfenning.android.here2beer.PubActivity;
import uk.co.rossfenning.android.here2beer.SplashActivity;
import uk.co.rossfenning.android.here2beer.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class Here2BeerSteps extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    private LocationManager locationManager;
    private MainActivity mainActivity;

    public Here2BeerSteps() {
        super("uk.co.rossfenning.android.here2beer", MainActivity.class);
    }

    @Before
    public void resetApp() {
        mainActivity = getActivity();
        solo = new Solo(getInstrumentation(), getActivity());
    }
    
    @After
    public void destroyApp() {
        mainActivity.finish();
    }
    
    @UiThreadTest
    @Given("^I am at (-?[\\d.]+),(-?[\\d.]+)$")
    public void setLocation(final double latitude, final double longitude)
        throws NoSuchMethodException, IllegalAccessException,
        IllegalArgumentException, InvocationTargetException
    {
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);

        final String testProvider = "test";

        if (null == locationManager.getProvider(testProvider)) {
            locationManager.addTestProvider(
                "test", false, false, false, false, false, false, false,
                Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        }

        final Location testLocation = new Location(testProvider);
        testLocation.setLatitude(latitude);
        testLocation.setLongitude(longitude);
        testLocation.setTime(System.currentTimeMillis());
        testLocation.setAccuracy(0f);

        final Method locationJellyBeanFixMethod = Location.class.getMethod("makeComplete");
        if (locationJellyBeanFixMethod != null) {
            locationJellyBeanFixMethod.invoke(testLocation);
        }

        locationManager.setTestProviderEnabled(testProvider, true);
        locationManager.setTestProviderLocation(testProvider, testLocation);

    }

    @UiThreadTest
    @When("^I press the button for a random pub suggestion$")
    public void waitForSplashScreen() {

        solo.clickOnText("Take me here to beer!");
        solo.waitForActivity("SplashActivity", 60000);
        solo.waitForActivity("PubActivity", 60000);
    }

    @UiThreadTest
    @Then("^I should be suggested a pub out of \"([^\"]*)\"$")
    public void assertPubGivenIsOneOf(final String pubNames) throws Throwable {

        final TextView textView = solo.getText(1);
        
        assertNotNull(textView);
        assertThat(textView.getText().toString(), isIn(pubNames.split(",")));
    }

    @UiThreadTest
    @Given("^my device is unable to give my location for some reason$")
    public void setDeviceUnableToDoLocation() {
        mainActivity = getActivity();
        locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        locationManager.setTestProviderEnabled("test", false);
        locationManager.getProvider("passive");
    }

    @UiThreadTest
    @Then("^I should get an error message saying my location is unavailable$")
    public void assertErrorMessage() {
        assertThat(solo.searchText("Could not detect your location, sorry."), is(true));
    }
}