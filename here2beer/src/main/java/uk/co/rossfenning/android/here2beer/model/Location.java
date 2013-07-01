package uk.co.rossfenning.android.here2beer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Root(strict=false, name="location")
public class Location implements Serializable {

    @Element(name="lat")
    private double latitude;

    @Element(name="lng")
    private double longitude;
}
