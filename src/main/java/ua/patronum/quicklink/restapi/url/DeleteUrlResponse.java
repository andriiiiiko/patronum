package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteUrlResponse {

    public enum Error {
        OK,
        INVALID_ID,
        INVALID_ACCESS,
    }

    private Error error;

    public static DeleteUrlResponse success() {
        return builder().error(Error.OK).build();
    }

    public static DeleteUrlResponse failed(Error error) {
        return builder().error(error).build();
    }
}
