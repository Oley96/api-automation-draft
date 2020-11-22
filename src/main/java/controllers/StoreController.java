package controllers;

import assertions.AssertableResponse;
import payloads.OrderPayload;

/**
 * @author Vladimir Oleynik
 */
public class StoreController extends Controller {

    public AssertableResponse placeOrder(OrderPayload payload) {
        return new AssertableResponse(setUp()
                .body(payload)
                .when()
                .post("/store/order"));

    }

    public AssertableResponse findOrderById(int orderId) {
        return new AssertableResponse(setUp()
                .when()
                .get("/store/order/" + orderId));
    }

    public AssertableResponse deleteOrderById(int orderId) {
        return new AssertableResponse(setUp()
                .when()
                .delete("/store/order/" + orderId));
    }

    public AssertableResponse getPetInventories() {
        return new AssertableResponse(setUp()
                .when()
                .get("/store/inventory"));
    }
}
