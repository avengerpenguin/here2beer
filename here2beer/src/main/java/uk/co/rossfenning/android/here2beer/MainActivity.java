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

public class MainActivity extends Activity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {

                final Location location = MainActivity.this.getLocation();

                if (location == null) {
                    new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Could not find location")
                        .setMessage("Could not detect your location, sorry.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(final DialogInterface di, final int i) {
                                MainActivity.this.finish();
                            }
                        }).show();
                }
                
                final Intent randomIntent = new Intent(MainActivity.this, SplashActivity.class);
                randomIntent.putExtra("location", location);
                MainActivity.this.startActivity(randomIntent);
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
