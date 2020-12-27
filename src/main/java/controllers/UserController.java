package controllers;

import assertions.AssertableResponse;
import entities.payloads.UserPayload;
import io.qameta.allure.Step;

/**
 * @author Vladimir Oleynik
 */
public class UserController extends Configuration {

    @Step
    public AssertableResponse createUser(UserPayload userPayload) {
        return new AssertableResponse(super.setUp()
                .body(userPayload)
                .when()
                .post("/user"));
    }

    @Step
    public AssertableResponse loginUser(String userName, String password) {
        return new AssertableResponse(super.setUp()
                .params("username", userName)
                .param("password", password)
                .when().get("/user/login")
        );
    }

    @Step
    public AssertableResponse logoutUser() {
        return new AssertableResponse(super.setUp()
                .when()
                .get("/user/logout")
        );
    }

    @Step
    public AssertableResponse getUserByName(String userName) {
        return new AssertableResponse(super.setUp()
                .when()
                .get("/user/" + userName)
        );
    }

    @Step
    public AssertableResponse updateUserByName(String userName, UserPayload payload) {
        return new AssertableResponse(super.setUp()
                .body(payload)
                .when()
                .put("/user/" + userName)
        );
    }

    @Step
    public AssertableResponse deleteUserByName(String userName) {
        return new AssertableResponse(super.setUp()
                .when()
                .delete("/user/" + userName)
        );
    }

    @Step
    public AssertableResponse createArrayOfUsers(UserPayload[] payloads) {
        return new AssertableResponse(super.setUp()
                .body(payloads)
                .when()
                .post("/user/createWithArray"));
    }
}
