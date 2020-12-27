package controllers;

import assertions.AssertableResponse;
import entities.payloads.PetPayload;
import entities.payloads.enums.Status;
import io.qameta.allure.Step;

import java.io.File;

/**
 * @author Vladimir Oleynik
 */
public class PetController extends Configuration {

    @Step
    public AssertableResponse uploadImageToPetById(int id, String metaData, File file) {
        return new AssertableResponse(setUp()
                .formParam("additionalMetadata", metaData)
                .contentType("multipart/form-data")
                .multiPart("file", file, "image/jpeg")
                .when()
                .post("/pet/" + id + "/uploadImage"));
    }

    @Step
    public AssertableResponse addPet(PetPayload pet) {
        return new AssertableResponse(setUp()
                .body(pet)
                .when()
                .post("/pet"));
    }

    @Step
    public AssertableResponse updatePet(PetPayload pet) {
        return new AssertableResponse(setUp()
                .body(pet)
                .when()
                .put("/pet"));
    }

    @Step
    public AssertableResponse findPetsByStatus(Status status) {
        return new AssertableResponse(setUp()
                .queryParam("status", status.getName())
                .when()
                .get("/pet/findByStatus"));
    }

    @Step
    public AssertableResponse findPetById(int id) {
        return new AssertableResponse(setUp()
                .when()
                .get("/pet/" + id));
    }

    @Step
    public AssertableResponse updatePetNameAndStatusById(int id, String name, Status status) {
        return new AssertableResponse(setUp()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status.getName())
                .when()
                .post("/pet/" + id));
    }

    @Step
    public AssertableResponse deletePetById(int id) {
        return new AssertableResponse(setUp()
                .when()
                .delete("/pet/" + id));
    }
}
