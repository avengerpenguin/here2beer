package uk.co.rossfenning.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import uk.co.rossfenning.android.model.PlaceSearchResponse;
import uk.co.rossfenning.android.model.Result;

import java.util.List;


public class RandomPubActivity extends Activity {

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut
     * down then this Bundle contains the data it most recently supplied in
     * onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PlaceSearchResponse response
            = (PlaceSearchResponse) this.getIntent().getSerializableExtra("response");

        final List<Result> results = response.getResults();
        final Result firstResult = results.get(0);

        setContentView(R.layout.activity_main);

        final TextView pubView = (TextView) findViewById(R.id.pub_name);
        pubView.setText(firstResult.getName());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
