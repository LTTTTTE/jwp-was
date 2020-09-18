package webserver;

public enum Status {
    OK(200, "OK"),
    CREATED(201, "Created"),
    FOUND(302, "Found"),
    NOT_FOUND(404, "Not Found");

    private int code;
    private String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
