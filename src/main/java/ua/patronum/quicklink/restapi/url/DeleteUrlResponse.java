package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class DeleteUrlResponse extends Response {

    public static DeleteUrlResponse success() {
        return success(builder().build());
    }

    public static DeleteUrlResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
