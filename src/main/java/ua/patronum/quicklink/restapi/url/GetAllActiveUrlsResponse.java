package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllActiveUrlsResponse extends Response {

    private List<UrlDto> activeUrls;

    public static GetAllActiveUrlsResponse success(List<UrlDto> urls) {
        return success(builder().activeUrls(urls).build());
    }

    public static GetAllActiveUrlsResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
