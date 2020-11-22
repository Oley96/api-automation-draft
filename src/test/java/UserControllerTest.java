import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import controllers.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.UserPayload;
import responses.ApiResponse;
import responses.UserResponse;

import java.util.Locale;

import static assertions.CustomAssertions.assertions;
import static helpers.ActionHelper.*;
import static java.lang.String.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserControllerTest {

    private final UserController userController = new UserController();
    private final Faker faker = new Faker(new Locale("en"));
    private UserPayload newUser;

    @BeforeEach
    public void beforeEach() {
        newUser = actions().createUser();
    }


    @Test
    @DisplayName("user can register")
    public void userCanRegisterNewProfile() {
        //when
        ApiResponse response = userController.createUser(newUser)
                .shouldHaveStatusCode(200)
                .asPojo(ApiResponse.class);

        //then
        assertEquals(valueOf(newUser.id()), response.getMessage());
    }


    @Test
    @DisplayName("get user by name")
    public void getUserByName() throws JsonProcessingException {
        //given
        userController.createUser(newUser).shouldHaveStatusCode(200);

        //when
        UserResponse response = userController.getUserByName(newUser.username())
                .shouldHaveStatusCode(200)
                .asPojo(UserResponse.class);
        //then
        assertTrue(assertions().isMatch(newUser, response));
    }

    @Test
    @DisplayName("User can login")
    public void userCanLogin() {
        //given
        userController.createUser(newUser)
                .shouldHaveStatusCode(200);
        //when and then
        userController.loginUser(newUser.username(), newUser.password())
                .shouldHaveStatusCode(200);
    }

    @Test
    @DisplayName("User can logout")
    public void loggedUserCanLogout() {
        //given
        userController.createUser(newUser)
                .shouldHaveStatusCode(200);
        userController.loginUser(newUser.username(), newUser.password())
                .shouldHaveStatusCode(200);

        //when, then
        userController.logoutUser()
                .shouldHaveStatusCode(200);
    }

    @Test
    @DisplayName("User can update profile info")
    public void userCanUpdateInfo() throws JsonProcessingException {
        //given
        userController.createUser(newUser).shouldHaveStatusCode(200);
        UserPayload updatedUser = actions().createUser();

        //when
        userController.updateUserByName(newUser.username(), updatedUser).shouldHaveStatusCode(200);

        //then
        UserResponse response = userController.getUserByName(updatedUser.username())
                .shouldHaveStatusCode(200)
                .asPojo(UserResponse.class);

        assertTrue(assertions().isMatch(updatedUser, response));
    }

    @Test
    @DisplayName("User can delete profile")
    public void userCanDeleteProfile() {
        //given
        userController.createUser(newUser).shouldHaveStatusCode(200);

        //when
        userController.deleteUserByName(newUser.username()).shouldHaveStatusCode(200);

        //then
        ApiResponse response = userController.getUserByName(newUser.username())
                .shouldHaveStatusCode(404)
                .asPojo(ApiResponse.class);
        assertEquals("User not found", response.getMessage());
        assertEquals("error", response.getType());
    }

    @Test
    @DisplayName("User can create array of users")
    public void userCanCreateArrayOfUsers() {
        //given
        UserPayload newUser1 = actions().createUser();
        UserPayload newUser2 = actions().createUser();
        UserPayload[] payloads = {newUser1, newUser2};

        //when
        userController.createArrayOfUsers(payloads).shouldHaveStatusCode(200);

        //then
        userController.getUserByName(newUser1.username()).shouldHaveStatusCode(200);
        userController.getUserByName(newUser2.username()).shouldHaveStatusCode(200);
    }

    @Test
    @DisplayName("User can't get info by invalid username")
    public void userCantGetInfoByInvalidUsername() {
        //when
        ApiResponse response = userController.getUserByName(faker.random().hex(10).toLowerCase())
                .shouldHaveStatusCode(404)
                .asPojo(ApiResponse.class);

        //then
        assertEquals("User not found", response.getMessage());
    }

    @Test
    @DisplayName("User can't delete profile by invalid username")
    public void userCantDeleteProfileByInvalidUsername() {
        //when, then
        userController.deleteUserByName(faker.name().username() + faker.random().nextInt(10000))
                .shouldHaveStatusCode(404);
    }
}

