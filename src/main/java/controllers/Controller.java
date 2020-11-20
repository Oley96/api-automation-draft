package controllers;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

/**
 * @author Vladimir Oleynik
 */
public class Controller {

    protected RequestSpecification setUp() {
        return RestAssured
                .given()
                .header("api_key", "test_oleynik")
                .baseUri("https://petstore.swagger.io")
                .basePath("/v2")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
}
