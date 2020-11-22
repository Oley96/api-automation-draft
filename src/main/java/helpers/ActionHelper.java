package helpers;

import com.github.javafaker.Faker;
import payloads.*;

import static java.util.Arrays.asList;
import static payloads.Status.AVAILABLE;

/**
 * @author Vladimir Oleynik
 */
public class ActionHelper {
    private final Faker faker = new Faker();

    public static ActionHelper actions() {
        return new ActionHelper();
    }

    private ActionHelper() {
    }

    public PetPayload createPet() {
        return new PetPayload()
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

    public OrderPayload createOrder() {
        return new OrderPayload()
                .id(faker.random().nextInt(1000))
                .petId(faker.number().numberBetween(10000, 9000))
                .quantity(faker.random().nextInt(50))
                .shipDate("2020-11-21T17:32:37.461+0000")
                .status("approve")
                .complete(true);
    }

    public UserPayload createUser() {
        return new UserPayload()
                .id(faker.random().nextInt(10000))
                .username(faker.name().username())
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password())
                .phone(faker.phoneNumber().cellPhone())
                .userStatus(faker.random().nextInt(200));
    }

}
