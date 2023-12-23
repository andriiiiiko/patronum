package ua.patronum.quicklink.auth.dto.registration;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegistrationResponse {
    private Error error;

    public enum Error {
        OK,
        USER_ALREADY_EXISTS,
        INVALID_USERNAME,
        INVALID_PASSWORD,
        INVALID_CONFIRM_PASSWORD,
        INVALID_RACE
    }

    public static RegistrationResponse success() {
        return builder().error(Error.OK).build();
    }

    public static RegistrationResponse failed(Error error) {
        return builder().error(error).build();
    }
}