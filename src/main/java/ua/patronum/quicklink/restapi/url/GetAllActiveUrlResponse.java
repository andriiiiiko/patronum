package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import ua.patronum.quicklink.data.entity.Url;

import java.util.List;

@Data
@Builder
public class GetAllActiveUrlResponse {

    public enum Error {
        OK,
        EMPTY_LIST,
    }

    private List<Url> activeUrls;

    private Error error;

    public static GetAllActiveUrlResponse success(List<Url> urls) {
        return builder().activeUrls(urls).error(Error.OK).build();
    }

    public static GetAllActiveUrlResponse failed(Error error) {
        return builder().error(error).build();
    }
}
