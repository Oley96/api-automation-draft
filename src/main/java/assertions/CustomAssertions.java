package assertions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import payloads.Status;
import responses.PetResponse;

import static java.util.Arrays.*;

/**
 * @author Vladimir Oleynik
 */
public class CustomAssertions {
    private final ObjectMapper mapper = new ObjectMapper();

    private CustomAssertions() {
    }

    public static CustomAssertions assertions() {
        return new CustomAssertions();
    }

    public boolean checkPetStatuses(PetResponse[] pets, Status status) {
        return pets.length == stream(pets).filter(i -> i.getStatus()
                .equals(status.getName()))
                .count();
    }

    public boolean isMatch(Object pet1, Object pet2) throws JsonProcessingException {
        return mapper.writeValueAsString(pet1).equals(mapper.writeValueAsString(pet2));
    }




}
