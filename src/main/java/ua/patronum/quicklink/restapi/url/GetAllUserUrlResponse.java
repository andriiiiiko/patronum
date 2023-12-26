package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllUserUrlResponse {

    public enum Error {
        OK,
    }

    private List<UrlDto> urls;

    private Error error;

    public static GetAllUserUrlResponse success(List<UrlDto> urls) {
        return builder().urls(urls).error(Error.OK).build();
    }

    public static GetAllUserUrlResponse failed(Error error) {
        return builder().error(error).build();
    }
}
