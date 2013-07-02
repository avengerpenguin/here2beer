package uk.co.rossfenning.android.here2beer.client;

import org.apache.commons.io.IOUtils;
import org.simpleframework.xml.core.Persister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.rossfenning.android.here2beer.model.PlaceSearchResponse;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class PlaceSearchClient implements HttpClient<PlaceSearchResponse> {

    private static final Logger logger = LoggerFactory.getLogger(PlaceSearchClient.class);

    @Override
    public PlaceSearchResponse fetch(final URL url) {
        try {
            logger.info("Opening connection to: " + url);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setConnectTimeout(20 * 1000);
            urlConnection.setDoInput(true);
            urlConnection.connect();
            
            logger.info("Opening stream...");
            final InputStream responseStream = urlConnection.getInputStream();
            final String response = IOUtils.toString(responseStream, "UTF-8");
            logger.info("Got response: " + response);
            logger.info("Parsing response...");
            return new Persister().read(PlaceSearchResponse.class, response);
        }
        catch (final Exception ex) {
            logger.info("Bad error: " + ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
}
