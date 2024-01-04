package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class ExtensionTimeResponce extends Response{

//    private String newExpirationDate;

    public static ExtensionTimeResponce success() {
        return success(builder().build());
    }

    public static ExtensionTimeResponce failed(Error error) {
        return failed(builder().build(), error);
    }
}
