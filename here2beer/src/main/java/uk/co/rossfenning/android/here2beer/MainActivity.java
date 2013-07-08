package uk.co.rossfenning.android.here2beer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TakeMeHereToBeerFragment fragment
            = (TakeMeHereToBeerFragment) getSupportFragmentManager().findFragmentById(R.id.button_fragment);
            
        final TextView milesText = (TextView) findViewById(R.id.miles_text);
        float radiusInMiles = fragment.getPubRequest().getRadius() / 1609.344f;
        milesText.setText(String.format("%.2f mile(s)", radiusInMiles));
        
        final RadiusSelectorView radiusSelectorView
            = (RadiusSelectorView) findViewById(R.id.radius_selector);
        
        radiusSelectorView.setPubRequest(fragment.getPubRequest());
        
        radiusSelectorView.setOnRadiusChangedListener(new RadiusSelectorView.OnRadiusChangeListener() {
            @Override
            public void onRadiusChanged(final float newRadius) {
                float radiusInMiles = newRadius / 1609.344f;
                milesText.setText(String.format("%.2f mile(s)", radiusInMiles));
            }
        });
        
            
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
