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

    private OnCompletionListener<T> listener;
    
    public interface OnCompletionListener<T2> {
        void onCompletion(T2 response);
    }
    
    public HttpFetchTask(final HttpClient<T> client, final OnCompletionListener<T> listener) {
        this.listener = listener;
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
        if (listener != null) {
            listener.onCompletion(response);
        }
    }
}
