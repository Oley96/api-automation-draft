package responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserRegistarationResponse {

	@JsonProperty("code")
	private int code;

	@JsonProperty("type")
	private String type;

	@JsonProperty("message")
	private String message;
}