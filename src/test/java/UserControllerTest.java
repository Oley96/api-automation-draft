import com.github.javafaker.Faker;
import controllers.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.CreateUserPayload;
import payloads.UpdateUserPayload;
import responses.DefaultResponse;
import responses.GetUserResponse;
import responses.UserRegistarationResponse;

import java.util.Locale;

import static conditions.Conditions.*;
import static java.lang.String.*;
import static org.junit.jupiter.api.Assertions.*;


public class UserTest {

    private final UserController userController = new UserController();
    private final Faker faker = new Faker(new Locale("en"));
    CreateUserPayload newUser;

    @BeforeEach
    public void beforeEach() {
        newUser = new CreateUserPayload()
                .id(faker.random().nextInt(10000))
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(faker.random().nextInt(200));
    }


    @Test
    @DisplayName("user can register")
    public void userCanRegisterNewProfile() {
        UserRegistarationResponse response = userController.createUser(newUser)
                .shouldHave(statusCode(200))
                .asPojo(UserRegistarationResponse.class);

        assertEquals(valueOf(newUser.id()), response.getMessage());
    }


    @Test
    @DisplayName("get user by name")
    public void getUserByName() {
        userController.createUser(newUser).shouldHave(statusCode(200));

        GetUserResponse response = userController.getUserByName(newUser.username())
                .shouldHave(statusCode(200))
                .asPojo(GetUserResponse.class);

        assertAll(
                () -> assertEquals(newUser.firstName(), response.getFirstName()),
                () -> assertEquals(newUser.lastName(), response.getLastName()),
                () -> assertEquals(newUser.password(), response.getPassword()),
                () -> assertEquals(newUser.userStatus(), response.getUserStatus()),
                () -> assertEquals(newUser.phone(), response.getPhone()),
                () -> assertEquals(newUser.id(), response.getId()),
                () -> assertEquals(newUser.email(), response.getEmail()),
                () -> assertEquals(newUser.username(), response.getUsername())
        );

    }

    @Test
    @DisplayName("User can login with valid creds")
    public void userCanLogin() {

        userController.createUser(newUser)
                .shouldHave(statusCode(200));

        userController.loginUser(newUser.username(), newUser.password())
                .shouldHave(statusCode(200));
    }

    @Test
    @DisplayName("User can logout")
    public void loggedUserCanLogout() {
        userController.createUser(newUser)
                .shouldHave(statusCode(200));

        userController.loginUser(newUser.username(), newUser.password())
                .shouldHave(statusCode(200));

        userController.logoutUser()
                .shouldHave(statusCode(200));
    }

    @Test
    @DisplayName("User can update profile info")
    public void userCanUpdateInfo() {
        userController.createUser(newUser).shouldHave(statusCode(200));

        UpdateUserPayload updatedInfo = new UpdateUserPayload()
                .id(faker.random().nextInt(10000))
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(faker.random().nextInt(200));


        userController.updateUserByName(newUser.username(), updatedInfo).shouldHave(statusCode(200));

        GetUserResponse response = userController.getUserByName(updatedInfo.username())
                .shouldHave(statusCode(200))
                .asPojo(GetUserResponse.class);

        assertAll(
                () -> assertEquals(updatedInfo.firstName(), response.getFirstName()),
                () -> assertEquals(updatedInfo.lastName(), response.getLastName()),
                () -> assertEquals(updatedInfo.password(), response.getPassword()),
                () -> assertEquals(updatedInfo.userStatus(), response.getUserStatus()),
                () -> assertEquals(updatedInfo.phone(), response.getPhone()),
                () -> assertEquals(updatedInfo.id(), response.getId()),
                () -> assertEquals(updatedInfo.email(), response.getEmail()),
                () -> assertEquals(updatedInfo.username(), response.getUsername())
        );


    }

    @Test
    @DisplayName("User can delete profile")
    public void userCanDeleteProfile() {
        userController.createUser(newUser).shouldHave(statusCode(200));
        userController.deleteUserByName(newUser.username()).shouldHave(statusCode(200));

        DefaultResponse response = userController.getUserByName(newUser.username())
                .shouldHave(statusCode(404))
                .asPojo(DefaultResponse.class);

        assertEquals("User not found", response.getMessage());
        assertEquals("error", response.getType());

    }

    @Test
    @DisplayName("User can create array of users")
    public void userCanCreateArrayOfUsers() {

        CreateUserPayload newUser1 = new CreateUserPayload()
                .id(faker.random().nextInt(10000))
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(faker.random().nextInt(200));

        CreateUserPayload newUser2 = new CreateUserPayload()
                .id(faker.random().nextInt(10000))
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(faker.random().nextInt(200));

        CreateUserPayload[] payloads = {newUser1, newUser2};

        userController.createArrayOfUsers(payloads).shouldHave(statusCode(200));

        userController.getUserByName(newUser1.username()).shouldHave(statusCode(200));
        userController.getUserByName(newUser2.username()).shouldHave(statusCode(200));
    }

    @Test
    @DisplayName("User can't get info by invalid username")
    public void userCantGetInfoByInvalidUsername() {

        DefaultResponse response = userController.getUserByName(faker.random().hex(10).toLowerCase())
                .shouldHave(statusCode(404))
                .asPojo(DefaultResponse.class);

        assertEquals("User not found", response.getMessage());
    }


    @Test
    @DisplayName("User can't delete profile by invalid username")
    public void userCantDeleteProfileByInvalidUsername() {
        userController.deleteUserByName(faker.random().hex(10).toLowerCase()).shouldHave(statusCode(404));
    }

}
