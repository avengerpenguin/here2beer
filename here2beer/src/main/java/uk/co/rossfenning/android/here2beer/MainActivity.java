package uk.co.rossfenning.android.here2beer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private final PubRequest pubRequest = new PubRequest();
    
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView milesText = (TextView) findViewById(R.id.miles_text);
        float radiusInMiles = pubRequest.getRadius() / 1609.344f;
        milesText.setText(String.format("%.2f mile(s)", radiusInMiles));
        
        final RadiusSelectorView radiusSelectorView
            = (RadiusSelectorView) findViewById(R.id.radius_selector);
        
        radiusSelectorView.setPubRequest(pubRequest);
        
        radiusSelectorView.setOnRadiusChangedListener(new RadiusSelectorView.OnRadiusChangeListener() {

            public void onRadiusChanged(final float newRadius) {
                float radiusInMiles = newRadius / 1609.344f;
                milesText.setText(String.format("%.2f mile(s)", radiusInMiles));
            }
        });
        
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {

                final Location location = MainActivity.this.getLocation();
                Log.d(getClass().getSimpleName(), "Final location: " + location);
                if (location == null) {
                    new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Could not find location.")
                        .setMessage("Could not detect your location, sorry. Ensure you have a working GPS or network signal and try again.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface di, final int i) {
                                // do nothing
                            }
                        }).show();
                }
                else {
                    pubRequest.setLatitude(location.getLatitude());
                    pubRequest.setLongitude(location.getLongitude());
                
                    final Intent randomIntent = new Intent(MainActivity.this, SplashActivity.class);
                    randomIntent.putExtra("pub_request", pubRequest);
                    MainActivity.this.startActivity(randomIntent);
                }
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
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
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

    public PubRequest getPubRequest() {
        return pubRequest;
    }

}
