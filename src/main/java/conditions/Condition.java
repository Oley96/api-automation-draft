package conditions;

import io.restassured.response.Response;

/**
 * @author Vladimir Oleynik
 */
public interface Condition {

    void check(Response response);
}
