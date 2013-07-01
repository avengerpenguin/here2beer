package uk.co.rossfenning.android.here2beer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Root(strict=false, name="geometry")
public class Geometry implements Serializable {

    @Element
    private Location location;
}
