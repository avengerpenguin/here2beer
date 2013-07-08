package uk.co.rossfenning.android.here2beer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class TakeMeHereToBeerFragment extends Fragment {

    private PubRequest pubRequest = new PubRequest();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_button, container, false);

        final Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final Location location = TakeMeHereToBeerFragment.this.getLocation();
                Log.d(getClass().getSimpleName(), "Final location: " + location);
                if (location == null) {
                    new AlertDialog.Builder(getActivity())
                        .setTitle("Could not find location.")
                        .setMessage("Could not detect your location, sorry. Ensure you have a working GPS or network signal and try again.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface di, final int i) {
                                // do nothing
                            }
                        }).show();
                }
                else {
                    getPubRequest().setLatitude(location.getLatitude());
                    getPubRequest().setLongitude(location.getLongitude());
                
                    final Intent randomIntent = new Intent(getActivity(), FindActivity.class);
                    randomIntent.putExtra("pub_request", getPubRequest());
                    TakeMeHereToBeerFragment.this.startActivity(randomIntent);
                }
            }
        });
        
        return v;
    }
    
    public Location getLocation() {

        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        final LocationManager locationManager =
            (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

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

    /**
     * @return the pubRequest
     */
    public PubRequest getPubRequest() {
        return pubRequest;
    }

    /**
     * @param pubRequest the pubRequest to set
     */
    public void setPubRequest(final PubRequest pubRequest) {
        this.pubRequest = pubRequest;
    }
}
