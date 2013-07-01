package uk.co.rossfenning.android.here2beer.test;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;
import com.jayway.android.robotium.solo.Solo;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.rossfenning.android.here2beer.MainActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MainActivitySteps extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;
    private LocationManager locationManager;
    private MainActivity mainActivity;

    public MainActivitySteps() {
        super("uk.co.rossfenning.android.here2beer", MainActivity.class);
    }

    @Before
    public void resetApp() {
        mainActivity = getActivity();
        solo = new Solo(getInstrumentation(), mainActivity);
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
        if (locationJellyBeanFixMethod != null && locationJellyBeanFixMethod.isAccessible()) {
            locationJellyBeanFixMethod.invoke(testLocation);
        }

        locationManager.setTestProviderEnabled(testProvider, true);
        locationManager.setTestProviderLocation(testProvider, testLocation);

    }

    @UiThreadTest
    @When("^I press the button for a random pub suggestion$")
    public void waitForPubSuggestion() {

        solo.clickOnText("Take me from here to beer!");
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

    @When("^I am suggested a pub$")
    public void pubSuggested() {
        waitForPubSuggestion();
    }

    @UiThreadTest
    @Then("^I should see its address$")
    public void assertAddressPresent() {
        final TextView addressView
            = (TextView) solo.getView(uk.co.rossfenning.android.here2beer.R.id.pub_address);
        assertThat(addressView.getText().toString(), not(isEmptyOrNullString()));
    }
    
    @Given("^I have been suggested a pub$")
    public void pubHasBeenSuggested() {
        waitForPubSuggestion();
    }

    @UiThreadTest
    @When("^I press the button saying \"([^\"]*)\"$")
    public void pressButton(final String text) {
        // Express the Regexp above with the code you wish you had
        solo.clickOnText(text);
    }

    @UiThreadTest
    @Then("^I should be sent to Google Maps to show walking directions to the suggested pub$")
    public void assertGoogleMapsLoaded() {
        assertTrue(solo.waitForLogMessage(
            "START u0 {act=android.intent.action.VIEW dat=http://maps.google.com/maps", 60000));
    }

}
