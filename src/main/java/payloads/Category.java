package payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Setter
@Getter
public class Category {

    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;
}