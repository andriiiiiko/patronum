package ua.patronum.quicklink.restapi.auth;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationResponse {

    private Error error;

    public static RegistrationResponse success() {
        return builder().error(Error.OK).build();
    }

    public static RegistrationResponse failed(Error error) {
        return builder().error(error).build();
    }

    public enum Error {
        OK,
        USER_ALREADY_EXISTS,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        INVALID_CONFIRM_PASSWORD;
    }
}