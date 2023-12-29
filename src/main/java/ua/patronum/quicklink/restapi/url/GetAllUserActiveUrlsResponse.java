package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllUserActiveUrlsResponse extends Response {

    private List<UrlDto> activeUserUrls;

    public static GetAllUserActiveUrlsResponse success(List<UrlDto> urls) {
        return success(builder().activeUserUrls(urls).build());
    }

    public static GetAllUserActiveUrlsResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
