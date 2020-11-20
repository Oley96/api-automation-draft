package assertions;

import conditions.Condition;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;


/**
 * @author Vladimir Oleynik
 */
@RequiredArgsConstructor
public class AssertableResponse {

    private final Response response;

    public AssertableResponse shouldHave(Condition condition) {
        condition.check(response);
        return this;
    }

    public <T> T asPojo(Class<T> tClass) {
        return this.response.as(tClass);
    }

    public Headers headers() {
        return this.response.getHeaders();
    }







}
