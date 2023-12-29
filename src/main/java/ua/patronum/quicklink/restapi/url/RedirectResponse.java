package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class RedirectResponse extends Response {

    private String originalUrl;

    public static RedirectResponse success(String originalUrl) {
        return success(builder().originalUrl(originalUrl).build());
    }

    public static RedirectResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
