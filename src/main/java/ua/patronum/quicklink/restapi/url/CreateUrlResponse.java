package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUrlResponse {

    public enum Error {
        OK,
        INVALID_OLD_VALID_URL,
        EMPTY_NEW_URL,
    }

    private Error error;

    public static CreateUrlResponse success() {
        return builder().error(Error.OK).build();
    }

    public static CreateUrlResponse failed(Error error) {
        return builder().error(error).build();
    }
}
