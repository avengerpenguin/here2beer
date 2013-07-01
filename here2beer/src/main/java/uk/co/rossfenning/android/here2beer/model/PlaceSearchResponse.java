package uk.co.rossfenning.android.here2beer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@Root(name = "PlaceSearchResponse", strict = false)
public class PlaceSearchResponse implements Serializable {

    @ElementList(inline = true, name="item")
    private List<Place> results;
}
