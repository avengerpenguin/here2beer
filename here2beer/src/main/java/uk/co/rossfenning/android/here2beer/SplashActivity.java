package uk.co.rossfenning.android.here2beer;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import uk.co.rossfenning.android.here2beer.client.HttpClient;
import uk.co.rossfenning.android.here2beer.client.PlaceSearchClient;
import uk.co.rossfenning.android.here2beer.client.HttpFetchTask;
import uk.co.rossfenning.android.here2beer.model.PlaceSearchResponse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends Activity {

    private final HttpClient<PlaceSearchResponse> client;

    public SplashActivity() throws MalformedURLException, IOException {

        this.client = new PlaceSearchClient();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final PubRequest pubRequest
            = (PubRequest) this.getIntent().getSerializableExtra("pub_request");

        URL url;
        try {
            url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?key="
                + getString(R.string.google_key)
                + "&location=" + pubRequest.getLatitude() + "," + pubRequest.getLongitude()
                + "&radius=" + pubRequest.getRadius() + "&sensor=false&types=bar");

            final ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {

                Log.i(getClass().getSimpleName(), "Fetching pub...");

                final HttpFetchTask<PlaceSearchResponse> httpFetchTask
                    = new HttpFetchTask(client, this, PubActivity.class);
                httpFetchTask.execute(url);

            }
            else {
                new AlertDialog.Builder(this)
                    .setTitle("Internet Connection Required")
                    .setMessage("An active Internet connection is required to use this app.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(final DialogInterface di, final int i) {
                            SplashActivity.this.finish();
                        }
                })
                    .show();
            }
        } catch (final MalformedURLException ex) {
            Log.e(getClass().getSimpleName(), "Could not create Google URL!", ex);
        }
    }

}