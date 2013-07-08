package uk.co.rossfenning.android.here2beer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Root(strict = false, name = "result")
public class Pub implements Serializable {

    @Element
    private String id;

    @Element
    private String name;

    @Element
    private String vicinity;

    @Element
    private Geometry geometry;
    
    @Override
    public String toString() {
        return name;
    }
}
