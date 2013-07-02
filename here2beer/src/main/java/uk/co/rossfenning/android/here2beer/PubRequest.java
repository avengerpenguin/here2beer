package uk.co.rossfenning.android.here2beer;

import lombok.Data;

import java.io.Serializable;

@Data
public class PubRequest implements Serializable {
    private double latitude;
    private double longitude;
    private float radius = 1000;

}
