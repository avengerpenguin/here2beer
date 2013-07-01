package uk.co.rossfenning.android.here2beer.client;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.net.URL;

public class HttpFetchTask<T extends Serializable>
    extends AsyncTask<URL, Void, T>
{

    private final HttpClient<T> client;
    private final Activity callingActivity;
    private final Class<Activity> nextActivity;

    public HttpFetchTask(
        final HttpClient<T> client,
        final Activity callingActivity,
        final Class<Activity> nextActivity)
    {
        this.callingActivity = callingActivity;
        this.nextActivity = nextActivity;
        this.client = client;
    }

    @Override
    protected T doInBackground(final URL... params) {
        Log.i(getClass().getSimpleName(), "Doing request...");
        final T response = client.fetch(params[0]);
        Log.i("HttpFetchTask", "Got response: " + response);
        return response;
    }

    @Override
    protected void onPostExecute(final T response) {
        Intent intent = new Intent(callingActivity, nextActivity);
        intent.putExtra("response", response);
        callingActivity.startActivity(intent);
        callingActivity.finish();
    }
}
