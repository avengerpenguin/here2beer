package uk.co.rossfenning.android.test;

import android.test.ActivityInstrumentationTestCase2;
import uk.co.rossfenning.android.RandomPubActivity;

public class RandomPubActivityTest extends ActivityInstrumentationTestCase2<RandomPubActivity> {

    public RandomPubActivityTest() {
        super("uk.co.rossfenning.android", RandomPubActivity.class); 
    }

    public void testActivityHasPubTitle() {
        RandomPubActivity activity = getActivity();
        assertNotNull(activity);
    }
}

