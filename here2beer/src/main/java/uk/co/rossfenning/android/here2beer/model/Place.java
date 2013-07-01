package uk.co.rossfenning.android.here2beer.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Root(strict=false, name="result")
public class Place implements Serializable {

    @Element
    private String name;
    
    @Element
    private String vicinity;
}
