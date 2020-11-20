package conditions;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

/**
 * @author Vladimir Oleynik
 */
@RequiredArgsConstructor
public class StatusCodeCondition implements Condition {

    private final int statusCode;

    @Override
    public void check(Response response) {
        response.then().assertThat().statusCode(statusCode);
    }
}
