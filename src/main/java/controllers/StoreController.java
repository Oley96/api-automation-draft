package controllers;

import assertions.AssertableResponse;
import entities.payloads.OrderPayload;
import io.qameta.allure.Step;

/**
 * @author Vladimir Oleynik
 */
public class StoreController extends Configuration {

    @Step
    public AssertableResponse placeOrder(OrderPayload payload) {
        return new AssertableResponse(setUp()
                .body(payload)
                .when()
                .post("/store/order"));
    }

    @Step
    public AssertableResponse findOrderById(int orderId) {
        return new AssertableResponse(setUp()
                .when()
                .get("/store/order/" + orderId));
    }

    @Step
    public AssertableResponse deleteOrderById(int orderId) {
        return new AssertableResponse(setUp()
                .when()
                .delete("/store/order/" + orderId));
    }
}
