package uk.co.rossfenning.android.here2beer;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import uk.co.rossfenning.android.here2beer.model.Pub;


public class PubActivity extends FragmentActivity {

    private SensorManager sensorManager;
    private ShakeEventListener sensorListener;
    private Pub pub;
    private FavouritesDAO favDao = new FavouritesDAO(this);
    private PubRequest pubRequest;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.pub = (Pub) this.getIntent().getSerializableExtra("pub");
        this.pubRequest = (PubRequest) this.getIntent().getSerializableExtra("request");
        
        setContentView(R.layout.activity_pub);

        final TextView pubView = (TextView) findViewById(R.id.pub_name);
        pubView.setText(pub.getName());

        final TextView addressView = (TextView) findViewById(R.id.pub_address);
        addressView.setText(pub.getVicinity());

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
                    pub.getGeometry().getLocation().getLatitude(),
                    pub.getGeometry().getLocation().getLongitude())));

                    PubActivity.this.finish();
                    startActivity(intent);
            }
        });

        final ImageView imageView = (ImageView) findViewById(R.id.star_view);
        final Button favouritesButton = (Button) findViewById(R.id.fav_button);

        if (favDao.isFavourite(pub)) {
            favouritesButton.setText(R.string.remove_favourites);
            imageView.setVisibility(View.VISIBLE);
            this.findViewById(R.layout.activity_pub).invalidate();
        }

        favouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favDao.isFavourite(pub)) {
                    favDao.removeFromFavourites(pub);
                    Toast.makeText(PubActivity.this, pub.getName() + " removed from favourites.", 1).show();
                    favouritesButton.setText(R.string.save_favourites);
                    imageView.setVisibility(View.INVISIBLE);
                }
                else {
                    favDao.addToFavourites(pub);
                    Toast.makeText(PubActivity.this, pub.getName() + " saved to favourites.", 1).show();
                    favouritesButton.setText(R.string.remove_favourites);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });

        final TakeMeHereToBeerFragment fragment
            = (TakeMeHereToBeerFragment) getSupportFragmentManager().findFragmentById(R.id.button_fragment);

        fragment.setPubRequest(pubRequest);
        fragment.setPreviousPub(pub);

        final Button anotherButton = (Button) findViewById(R.id.button);
        anotherButton.setText(R.string.somewhere_else);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorListener = new ShakeEventListener();

        sensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            public void onShake() {
                anotherButton.performClick();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorListener);
        super.onStop();
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

    @Override
    public void onBackPressed() {
        PubActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case R.id.action_favourites:
            final Intent intent = new Intent(this, FavouritesActivity.class);
            this.startActivity(intent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
}
