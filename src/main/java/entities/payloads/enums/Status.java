package entities.payloads.enums;

import lombok.ToString;

/**
 * @author Vladimir Oleynik
 */
@ToString
public enum Status {

    AVAILABLE("available"),
    PENDING("pending"),
    SOLD("sold");

    Status(String name) {
        this.name = name;
    }

    private final String name;

    public String getName() {
        return name;
    }
}
