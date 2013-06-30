package uk.co.rossfenning.android.test;

import android.app.Instrumentation.ActivityMonitor;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.TextView;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.rossfenning.android.R;
import uk.co.rossfenning.android.RandomPubActivity;
import uk.co.rossfenning.android.SplashActivity;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RandomPubActivitySteps extends ActivityInstrumentationTestCase2<SplashActivity> {

    private SplashActivity splashActivity;
    private RandomPubActivity pubActivity;

    private List<String> expectedPubNames = Arrays.asList("The Wyvern",
        "Table Table",
        "Premier Inn Manchester Central",
        "The Paramount",
        "The Alibi",
        "True",
        "110 Restaurant and Bar",
        "The Waterhouse",
        "One Central Club",
        "Henry s Cafe Bar",
        "Tiger Lounge",
        "City Arms",
        "Opus Restaurant",
        "The Vine Inn",
        "Stalls Cafe Bar",
        "Bannatyne's Health Club",
        "Fab Cafe",
        "Albert Square",
        "The Slug And Lettuce",
        "BrewDog Manchester");

    public RandomPubActivitySteps() {
        super("uk.co.rossfenning.android", SplashActivity.class);
    }

    @UiThreadTest
    @When("^I wait for the app to load$")
    public void waitForSplashScreen() {
        splashActivity = getActivity();
        ActivityMonitor monitor = getInstrumentation().addMonitor(RandomPubActivity.class.getName(), null, false);
        
        pubActivity = (RandomPubActivity) monitor
            .waitForActivityWithTimeout(30000);

        assertNotNull(pubActivity);
    }

    @UiThreadTest
    @Then("^I should be given a random pub name$")
    public void seeRandomPubName() {
        final TextView textView = (TextView) pubActivity.findViewById(R.id.pub_name);
        assertNotNull(textView);
        assertThat(textView.getText().toString(), isIn(expectedPubNames));
    }
}
