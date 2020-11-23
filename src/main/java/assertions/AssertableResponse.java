package assertions;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


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

    public AssertableResponse shouldMatchWithJsonSchema(String path) {
        this.response.then().body(matchesJsonSchemaInClasspath("schemes/".concat(path)));
        return this;
    }

    public <T> T asPojo(Class<T> tClass) {
        return this.response.as(tClass);
    }

    public Headers getHeaders() {
        return this.response.getHeaders();
    }


}
