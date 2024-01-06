package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class ExtensionTimeResponse extends Response{

    public static ExtensionTimeResponse success() {
        return success(builder().build());
    }

    public static ExtensionTimeResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
