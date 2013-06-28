package uk.co.rossfenning.android;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import uk.co.rossfenning.android.client.HttpClient;
import uk.co.rossfenning.android.client.PlaceSearchClient;
import uk.co.rossfenning.android.client.HttpFetchTask;
import uk.co.rossfenning.android.model.PlaceSearchResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class SplashActivity extends Activity {

    private HttpClient<PlaceSearchResponse> client;

    public SplashActivity() throws MalformedURLException, IOException {

        this.client = new PlaceSearchClient();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Resources resources = this.getResources();
        final InputStream rawResource = resources.openRawResource(R.raw.google);
        final Properties properties = new Properties();
        try {
            properties.load(rawResource);
        } catch (final IOException ex) {
            Log.e(getClass().getSimpleName(), "Could not load properties!", ex);
        }

        URL url;
        try {
            url = new URL("https://maps.googleapis.com/maps/api/place/nearbysearch/xml?key="
                + properties.getProperty("google.api.key")
                + "&location=53.477626,-2.243872&rankby=distance&sensor=false&types=bar");

            final ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                Log.i(getClass().getSimpleName(), "Fetching pub...");
                final HttpFetchTask<PlaceSearchResponse> httpFetchTask
                    = new HttpFetchTask(client, this, RandomPubActivity.class);
                httpFetchTask.execute(url);
            } else {
                new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT)
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
