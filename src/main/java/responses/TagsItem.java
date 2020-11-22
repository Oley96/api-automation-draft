package responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class TagsItem{

	@JsonProperty("name")
	private String name;

	@JsonProperty("id")
	private long id;
}