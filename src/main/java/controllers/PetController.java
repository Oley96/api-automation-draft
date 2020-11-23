package controllers;

import assertions.AssertableResponse;
import payloads.PetPayload;
import payloads.enums.Status;

import java.io.File;

/**
 * @author Vladimir Oleynik
 */
public class PetController extends Controller {

    public AssertableResponse uploadImageToPetById(int id, String metaData, File file) {
        return new AssertableResponse(setUp()
                .formParam("additionalMetadata", metaData)
                .contentType("multipart/form-data")
                .multiPart("file", file, "image/jpeg")
                .when()
                .post("/pet/" + id + "/uploadImage"));
    }

    public AssertableResponse addPet(PetPayload pet) {
        return new AssertableResponse(setUp()
                .body(pet)
                .when()
                .post("/pet"));
    }

    public AssertableResponse updatePet(PetPayload pet) {
        return new AssertableResponse(setUp()
                .body(pet)
                .when()
                .put("/pet"));
    }

    public AssertableResponse findPetsByStatus(Status status) {
        return new AssertableResponse(setUp()
                .queryParam("status", status.getName())
                .when()
                .get("/pet/findByStatus"));
    }

    public AssertableResponse findPetById(int id) {
        return new AssertableResponse(setUp()
                .when()
                .get("/pet/" + id));
    }

    public AssertableResponse updatePetNameAndStatusById(int id, String name, Status status) {
        return new AssertableResponse(setUp()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status.getName())
                .when()
                .post("/pet/" + id));
    }

    public AssertableResponse deletePetById(int id) {
        return new AssertableResponse(setUp()
                .when()
                .delete("/pet/" + id));
    }
}
