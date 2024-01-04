package ua.patronum.quicklink.mvc;

public enum MvcError {
    USER_ALREADY_EXISTS("User already exists"),
    INVALID_CONFIRM_PASSWORD("Invalid confirm password"),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("Invalid username"),
    EXPIRED_URL("Expired URL"),
    INVALID_URL("Invalid URL");
    private final String errorMessage;

    MvcError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
