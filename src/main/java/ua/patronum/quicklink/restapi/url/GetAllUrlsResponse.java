package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
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
