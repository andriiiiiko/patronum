package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllUrlsResponse extends Response {

    private List<UrlDto> urls;

    public static GetAllUrlsResponse success(List<UrlDto> urls) {
        return success(builder().urls(urls).build());
    }

    public static GetAllUrlsResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
