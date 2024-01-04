package ua.patronum.quicklink.mvc.auth;

public enum RegistrationError {
    USER_ALREADY_EXISTS("User already exists"),
    INVALID_CONFIRM_PASSWORD("Invalid confirm password"),
    INVALID_PASSWORD("Invalid password"),
    INVALID_USERNAME("Invalid username");
    private final String errorMessage;

    RegistrationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
