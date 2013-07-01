package uk.co.rossfenning.android.client;

import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlaceSearchClientTest {
    
    private final URL mockUrl = getClass().getResource("/pubs.xml");
    private final PlaceSearchClient client = new PlaceSearchClient();

    @Test
    public void testClientFetchesSomething() throws IOException, Exception {
        assertThat(client.fetch(mockUrl), is(not(nullValue())));
    }
}
