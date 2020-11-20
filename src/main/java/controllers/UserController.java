package controllers;

import assertions.AssertableResponse;
import payloads.CreateUserPayload;
import payloads.UpdateUserPayload;

import java.util.List;

/**
 * @author Vladimir Oleynik
 */
public class UserController extends Controller {

    public AssertableResponse createUser(CreateUserPayload createUserPayload) {
        return new AssertableResponse(setUp()
                .body(createUserPayload)
                .when()
                .post("/user"));
    }

    public AssertableResponse loginUser(String userName, String password) {
        return new AssertableResponse(setUp()
                .params("username", userName)
                .param("password", password)
                .when().get("/user/login")
        );
    }

    public AssertableResponse logoutUser() {
        return new AssertableResponse(setUp()
                .when()
                .get("/user/logout")
        );
    }

    public AssertableResponse getUserByName(String userName) {
        return new AssertableResponse(setUp()
                .when()
                .get("/user/" + userName)
        );
    }

    public AssertableResponse updateUserByName(String userName, UpdateUserPayload payload) {
        return new AssertableResponse(setUp()
                .body(payload)
                .when()
                .put("/user/" + userName)
        );
    }

    public AssertableResponse deleteUserByName(String userName) {
        return new AssertableResponse(setUp()
                .when()
                .delete("/user/" + userName)
        );
    }

    public AssertableResponse createArrayOfUsers(CreateUserPayload[] payloads) {
        return new AssertableResponse(setUp()
                .body(payloads)
                .when()
                .post("/user/createWithArray"));
    }
}
