package uk.co.rossfenning.android.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Root(strict=false)
public class Result implements Serializable {

    @Element
    private String name;
}
