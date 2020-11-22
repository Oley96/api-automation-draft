import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.javafaker.Faker;
import controllers.StoreController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import payloads.PlaceOrderPayload;
import responses.ApiResponse;
import responses.OrderResponse;


import static assertions.CustomAssertions.*;
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
    public void userCanPlaceOrder() throws JsonProcessingException {
        OrderResponse response = storeController.placeOrder(order)
                .shouldHaveStatusCode(200)
                .asPojo(OrderResponse.class);

        assertTrue(assertions().isMatch(order, response));
    }

    @Test
    @DisplayName("User can see order info by id")
    public void getOrderInfoById() throws JsonProcessingException {
        //given
        storeController.placeOrder(order).shouldHaveStatusCode(200);

        //when
        OrderResponse response = storeController.findOrderById(order.id())
                .shouldHaveStatusCode(200)
                .asPojo(OrderResponse.class);

        //then
        assertTrue(assertions().isMatch(order, response));
    }


    @Test
    @DisplayName("user can delete order by id")
    public void userCanDeleteOrderById() {
        storeController.placeOrder(order).shouldHaveStatusCode(200);

        storeController.deleteOrderById(order.id()).shouldHaveStatusCode(200);

        ApiResponse response = storeController.findOrderById(order.id())
                .shouldHaveStatusCode(404)
                .asPojo(ApiResponse.class);

        assertEquals("Order not found", response.getMessage());
    }


}
