package payloads;

/**
 * @author Vladimir Oleynik
 */
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
