package uk.co.rossfenning.android.here2beer.client;

import java.net.URL;

public interface HttpClient<T> {

    T fetch(final URL url);
    
}
