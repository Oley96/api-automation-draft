package responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DefaultResponse {

    @JsonProperty("code")
    private int code;

    @JsonProperty("type")
    private String type;

    @JsonProperty("message")
    private String message;
}