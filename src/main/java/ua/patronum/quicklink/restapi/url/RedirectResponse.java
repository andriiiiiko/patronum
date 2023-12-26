package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RedirectResponse {

    public enum Error {
        OK,
        INVALID_SHORT_URL,
    }

    private String originalUrl;
    private Error error;

    public static RedirectResponse success(String originalUrl) {
        return builder().originalUrl(originalUrl).error(Error.OK).build();
    }

    public static RedirectResponse failed(Error error) {
        return builder().error(error).build();
    }
}