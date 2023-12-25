package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import ua.patronum.quicklink.data.entity.Url;

import java.util.List;

@Data
@Builder
public class GetAllUrlsResponse {
    private List<Url> urls;

    public enum Error {
        OK,
        EMPTY_LIST,
    }

    private GetAllUrlsResponse.Error error;

    public static GetAllUrlsResponse success(List<Url> urls) {
        return builder().urls(urls).error(GetAllUrlsResponse.Error.OK).build();
    }

    public static GetAllUrlsResponse failed(GetAllUrlsResponse.Error error) {
        return builder().error(error).build();
    }
}
