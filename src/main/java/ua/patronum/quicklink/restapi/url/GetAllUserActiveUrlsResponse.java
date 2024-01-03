package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class GetAllUserActiveUrlsResponse extends Response {

    private List<UrlDto> userUrls;

    public static GetAllUserActiveUrlsResponse success(List<UrlDto> urls) {
        return success(builder().userUrls(urls).build());
    }

    public static GetAllUserActiveUrlsResponse failed(Error error) {
        return failed(builder().build(), error);
    }
}
