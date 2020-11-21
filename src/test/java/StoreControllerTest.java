import com.github.javafaker.Faker;
import controllers.StoreController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.PlaceOrderPayload;
import responses.ApiResponse;
import responses.OrderResponse;


import static conditions.Conditions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Vladimir Oleynik
 */
public class StoreControllerTest {
    private final StoreController storeController = new StoreController();
    private PlaceOrderPayload order;
    private final Faker faker = new Faker();


    @BeforeEach
    public void setUp() {
        order = new PlaceOrderPayload()
                .id(faker.random().nextInt(1000))
                .petId(faker.number().numberBetween(10000, 9000))
                .quantity(faker.random().nextInt(50))
                .shipDate("2020-11-21T17:32:37.461+0000")
                .status("approve")
                .complete(true);
    }

    @Test
    @DisplayName("User can place an order for a pet")
    public void userCanPlaceOrder() {
        OrderResponse response = storeController.placeOrder(order)
                .shouldHave(statusCode(200))
                .asPojo(OrderResponse.class);

        assertAll(
                () -> assertEquals(order.id(), response.getId()),
                () -> assertEquals(order.petId(), response.getPetId()),
                () -> assertEquals(order.quantity(), response.getQuantity()),
                () -> assertEquals(order.shipDate(), response.getShipDate()),
                () -> assertEquals(order.status(), response.getStatus()),
                () -> assertEquals(order.complete(), response.isComplete())
        );
    }

    @Test
    @DisplayName("User can see order info by id")
    public void getOrderInfoById() {
        storeController.placeOrder(order).shouldHave(statusCode(200));

        OrderResponse response = storeController.findOrderById(order.id())
                .shouldHave(statusCode(200))
                .asPojo(OrderResponse.class);

        assertAll(
                () -> assertEquals(order.id(), response.getId()),
                () -> assertEquals(order.petId(), response.getPetId()),
                () -> assertEquals(order.quantity(), response.getQuantity()),
                () -> assertEquals(order.shipDate(), response.getShipDate()),
                () -> assertEquals(order.status(), response.getStatus()),
                () -> assertEquals(order.complete(), response.isComplete())
        );
    }


    @Test
    @DisplayName("user can delete order by id")
    public void userCanDeleteOrderById() {
        storeController.placeOrder(order).shouldHave(statusCode(200));

        storeController.deleteOrderById(order.id()).shouldHave(statusCode(200));

        ApiResponse response = storeController.findOrderById(order.id())
                .shouldHave(statusCode(404))
                .asPojo(ApiResponse.class);

        assertEquals("Order not found", response.getMessage());
    }


}
