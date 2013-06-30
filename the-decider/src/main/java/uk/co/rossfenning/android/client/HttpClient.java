package uk.co.rossfenning.android.client;

import java.net.URL;

public interface HttpClient<T> {

    T fetch(final URL url);
    
}
