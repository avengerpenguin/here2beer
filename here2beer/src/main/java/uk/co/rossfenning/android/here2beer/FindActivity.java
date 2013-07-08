package uk.co.rossfenning.android.here2beer;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import uk.co.rossfenning.android.here2beer.client.HttpClient;
import uk.co.rossfenning.android.here2beer.client.PlaceSearchClient;
import uk.co.rossfenning.android.here2beer.client.HttpFetchTask;
import uk.co.rossfenning.android.here2beer.model.PlaceSearchResponse;
import uk.co.rossfenning.android.here2beer.model.Pub;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class FindActivity extends Activity {

    private final HttpClient<PlaceSearchResponse> client;

    public FindActivity() throws MalformedURLException, IOException {

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

                final HttpFetchTask<PlaceSearchResponse> httpFetchTask = new HttpFetchTask(
                    client, new HttpFetchTask.OnCompletionListener<PlaceSearchResponse>() {

                    public void onCompletion(final PlaceSearchResponse response) {
                        final List<Pub> results = response.getResults();
                        final Pub randomPub = results.get(
                            new Random(System.currentTimeMillis()).nextInt(results.size()));
                        
                        final Intent intent = new Intent(FindActivity.this, PubActivity.class);
                        intent.putExtra("pub", randomPub);
                        FindActivity.this.startActivity(intent);
                        FindActivity.this.finish();
                    }
                });
                httpFetchTask.execute(url);

            }
            else {
                new AlertDialog.Builder(this)
                    .setTitle("Internet Connection Required")
                    .setMessage("An active Internet connection is required to use this app.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(final DialogInterface di, final int i) {
                            FindActivity.this.finish();
                        }
                })
                    .show();
            }
        } catch (final MalformedURLException ex) {
            Log.e(getClass().getSimpleName(), "Could not create Google URL!", ex);
        }
    }

}