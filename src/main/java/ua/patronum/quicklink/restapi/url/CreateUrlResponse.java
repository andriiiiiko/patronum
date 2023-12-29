package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class CreateUrlResponse extends Response {

    public static CreateUrlResponse success() {
        return success(builder().build());
    }

    public static CreateUrlResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
