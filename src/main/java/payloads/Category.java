package payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Setter
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Category {


    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;
}