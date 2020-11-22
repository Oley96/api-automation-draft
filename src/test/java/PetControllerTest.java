import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import controllers.PetController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.Category;
import payloads.PetPayload;
import payloads.TagsItem;
import responses.ApiResponse;
import responses.PetResponse;

import java.io.File;

import static assertions.CustomAssertions.assertions;
import static java.lang.String.*;
import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;
import static payloads.Status.*;

/**
 * @author Vladimir Oleynik
 */
public class PetControllerTest {

    private final PetController petController = new PetController();
    private final Faker faker = new Faker();
    private PetPayload pet;

    @BeforeEach
    public void setUp() {
        pet = new PetPayload()
                .id(faker.random().nextInt(1000))
                .category(new Category("Dogs", faker.random().nextInt(10000)))
                .name("Tomas" + faker.random().nextInt(10000))
                .photoUrls(asList("http://test.com", "http://aaa.com"))
                .tags(asList(
                        new TagsItem("dog", faker.random().nextInt(10000)),
                        new TagsItem("staff", faker.random().nextInt(10000)))
                )
                .status(AVAILABLE.getName());
    }

    @Test
    @DisplayName("User can upload image to pet info")
    public void userCanUploadImageToPetById() {
        //given
        File file = new File("src/test/resources/sampleFile.jpeg");

        //when
        ApiResponse response = petController.uploadImageToPetById(24, "test", file)
                .shouldHaveStatusCode(200)
                .asPojo(ApiResponse.class);

        //then
        assertEquals(format("additionalMetadata: test\nFile uploaded to ./%s, 4096 bytes", file.getName()),
                response.getMessage(), "Texts do not match");
    }


    @Test
    @DisplayName("User can add new pet")
    public void userCanAddNewPet() throws JsonProcessingException {
        //when
        PetResponse response = petController.addPet(pet)
                .shouldHaveStatusCode(200)
                .asPojo(PetResponse.class);

        //then
        assertTrue(assertions().isMatch(pet, response), "Objects do not match");
    }

    @Test
    @DisplayName("User can update existing pet")
    public void userCanUpdatePet() throws JsonProcessingException {
        //given
        petController.addPet(pet);
        PetPayload newPet = new PetPayload()
                .id(faker.random().nextInt(1000))
                .category(new Category("Dogs", faker.random().nextInt(10000)))
                .name("Tomas" + faker.random().nextInt(10000))
                .photoUrls(asList("http://test.com", "http://aaa.com"))
                .tags(asList(
                        new TagsItem("dog", faker.random().nextInt(10000)),
                        new TagsItem("staff", faker.random().nextInt(10000)))
                )
                .status(SOLD.getName());
        //when
        PetResponse response = petController.updatePet(newPet)
                .shouldHaveStatusCode(200)
                .asPojo(PetResponse.class);
        //then
        assertTrue(assertions().isMatch(newPet, response), "Objects do not match");
    }

    //need parametrize
    @Test
    @DisplayName("Should returns only pets with selected status")
    public void shouldReturnOnlyPetsWithSelectedStatus() {
        //when
        PetResponse[] pets = petController.findPetsByStatus(AVAILABLE)
                .shouldHaveStatusCode(200)
                .asPojo(PetResponse[].class);
        //then
        assertTrue(assertions().checkPetStatuses(pets, AVAILABLE), "Not all response contains selected status");
    }

    @Test
    @DisplayName("User can find pet by id")
    public void userCanFindPetById() {
        //given
        petController.addPet(pet).shouldHaveStatusCode(200);

        //when
        PetResponse response = petController.findPetById(pet.id())
                .shouldHaveStatusCode(200)
                .asPojo(PetResponse.class);
        //then
        assertEquals(pet.hashCode(), response.hashCode(), "Objects do not match");
    }

    @Test
    @DisplayName("User can update name and status for pet")
    public void userCanUpdateStatusAndNameForPet() {
        //given
        petController.addPet(pet).shouldHaveStatusCode(200);

        //when
        petController.updatePetNameAndStatusById(pet.id(), "Test", PENDING)
                .shouldHaveStatusCode(200);

        //then
        PetResponse response = petController.findPetById(pet.id()).shouldHaveStatusCode(200)
                .asPojo(PetResponse.class);
        assertAll(
                () -> assertEquals("Test", response.getName(), "Names do not match"),
                () -> assertEquals(PENDING.getName(), response.getStatus(), "Statuses do not match")
        );
    }

    @Test
    @DisplayName("Should delete pet by id")
    public void shouldDeletePetPyId() {
        //given
        petController.addPet(pet).shouldHaveStatusCode(200);

        //when
        petController.deletePetById(pet.id()).shouldHaveStatusCode(200);

        //then
        ApiResponse response = petController.findPetById(pet.id())
                .shouldHaveStatusCode(404)
                .asPojo(ApiResponse.class);
        assertEquals("Pet not found", response.getMessage(), "Pet exists");

    }
}