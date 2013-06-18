package uk.co.rossfenning.android.test;

import android.test.ActivityInstrumentationTestCase2;
import uk.co.rossfenning.android.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<RandomPubActivity> {

    public HelloAndroidActivityTest() {
        super(RandomPubActivity.class); 
    }

    public void testActivityHasPubTitle() {
        RandomPubActivity activity = getActivity();
        assertNotNull(activity);
    }
}

