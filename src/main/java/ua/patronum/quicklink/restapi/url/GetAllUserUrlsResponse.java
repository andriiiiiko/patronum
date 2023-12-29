package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllUserUrlsResponse extends Response {

    private List<UrlDto> userUrls;

    public static GetAllUserUrlsResponse success(List<UrlDto> urls) {
        return success(builder().userUrls(urls).build());
    }

    public static GetAllUserUrlsResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
