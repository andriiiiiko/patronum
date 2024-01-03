package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class GetAllActiveUrlsResponse extends Response {

    private List<UrlDto> urls;

    public static GetAllActiveUrlsResponse success(List<UrlDto> urls) {
        return success(builder().urls(urls).build());
    }

    public static GetAllActiveUrlsResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
