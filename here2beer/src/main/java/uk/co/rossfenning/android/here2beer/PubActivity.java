package uk.co.rossfenning.android.here2beer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import uk.co.rossfenning.android.here2beer.model.PlaceSearchResponse;
import uk.co.rossfenning.android.here2beer.model.Place;

import java.util.List;
import java.util.Random;

public class PubActivity extends Activity {

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
        
        final List<Place> results = response.getResults();
        final Place randomPlace = results.get(
            new Random(System.currentTimeMillis()).nextInt(results.size()));
        
        setContentView(R.layout.activity_pub);
        
        final TextView pubView = (TextView) findViewById(R.id.pub_name);
        pubView.setText(randomPlace.getName());
        
        final TextView addressView = (TextView) findViewById(R.id.pub_address);
        addressView.setText(randomPlace.getVicinity());
        
        final Button directionsButton = (Button) findViewById(R.id.directions_button);
        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                
                final Location currentLocation = getLocation();

                final Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse(
                        String.format(
                            "http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s&directionsmode=walking",
                            currentLocation.getLatitude(),
                            currentLocation.getLongitude(),
                            randomPlace.getGeometry().getLocation().getLatitude(),
                            randomPlace.getGeometry().getLocation().getLongitude())));

                startActivity(intent);
            }
        });
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public Location getLocation() {

        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.NO_REQUIREMENT);
        final LocationManager locationManager =
            (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        Location location = null;
        for (final String provider : locationManager.getAllProviders()) {
            Log.d(getClass().getSimpleName(), "Checking provider: " + provider);
            location = locationManager.getLastKnownLocation(provider);
            Log.d(getClass().getSimpleName(), "Found locatiion: " + location);
            if (location != null) {
                break;
            }
        }

        return location;
    }
}
