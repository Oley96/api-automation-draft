package assertions;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;


/**
 * @author Vladimir Oleynik
 */
@RequiredArgsConstructor
public class AssertableResponse {

    private final Response response;

    public AssertableResponse shouldHaveStatusCode(int code) {
        this.response.then().assertThat().statusCode(code);
        return this;
    }

    public <T> T asPojo(Class<T> tClass) {
        return this.response.as(tClass);
    }

    public Headers headers() {
        return this.response.getHeaders();
    }


}
