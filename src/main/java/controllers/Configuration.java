package controllers;

import api.ProjectConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * @author Vladimir Oleynik
 */
public class Configuration {
    private final ProjectConfig config;

    public Configuration() {
        config = ConfigFactory.create(ProjectConfig.class, System.getProperties());
    }


    public RequestSpecification setUp() {
        return RestAssured.given()
                .baseUri(config.baseUri())
                .basePath(config.basePath())
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .filters(getFilters());
    }

    private List<Filter> getFilters() {
        if (config.logging())
            return Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter(), new AllureRestAssured());
        return Collections.emptyList();
    }
}
