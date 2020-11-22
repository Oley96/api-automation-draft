package payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;

@ToString
@Accessors(fluent = true)
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class TagsItem {


    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private int id;
}