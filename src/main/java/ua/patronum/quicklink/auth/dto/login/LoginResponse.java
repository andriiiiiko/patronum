package ua.patronum.quicklink.auth.dto.login;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {

    private Error error;
    private String authToken;

    public enum Error {
        OK,
        INVALID_USER_NAME,
        INVALID_PASSWORD
    }

    public static LoginResponse success(String authToken) {
        return builder().error(Error.OK).authToken(authToken).build();
    }

    public static LoginResponse failed(Error error) {
        return builder().error(error).build();
    }
}