package ua.patronum.quicklink.restapi.url;

import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class GetAllUrlsResponse {

    public enum Error {
        OK,
        EMPTY_LIST,
    }
    private List<UrlDto> urls;
    private Error error;

    public static GetAllUrlsResponse success(List<UrlDto> urls) {
        return builder().urls(urls).error(Error.OK).build();
    }

    public static GetAllUrlsResponse failed(Error error) {
        return builder().error(error).build();
    }
}
