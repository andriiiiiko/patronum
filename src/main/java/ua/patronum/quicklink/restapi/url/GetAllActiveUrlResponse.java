package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllActiveUrlResponse {

    public enum Error {
        OK,
    }

    private List<UrlDto> activeUrls;

    private Error error;

    public static GetAllActiveUrlResponse success(List<UrlDto> urls) {
        return builder().activeUrls(urls).error(Error.OK).build();
    }

    public static GetAllActiveUrlResponse failed(Error error) {
        return builder().error(error).build();
    }
}
