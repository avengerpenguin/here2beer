package uk.co.rossfenning.android.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import uk.co.rossfenning.android.R;
import uk.co.rossfenning.android.RandomPubActivity;

public class RandomPubActivitySteps extends ActivityInstrumentationTestCase2<RandomPubActivity> {

    private RandomPubActivity activity;
    
    public RandomPubActivitySteps() {
        super("uk.co.rossfenning.android", RandomPubActivity.class);
    }

    @When("^I start the app$")
    public void startTheApp() {
        activity = getActivity();
    }

    @Then("^I should be given a random pub name$")
    public void seeRandomPubName() {
        final TextView textView = (TextView) activity.findViewById(R.id.pub_name);
        assertNotNull(textView);
    }
}
