package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import ua.patronum.quicklink.data.entity.Url;

import java.util.List;

@Data
@Builder
public class GetAllUrlsResponse {

    public enum Error {
        OK,
        EMPTY_LIST,
    }
    private List<Url> urls;

    private Error error;

    public static GetAllUrlsResponse success(List<Url> urls) {
        return builder().urls(urls).error(Error.OK).build();
    }

    public static GetAllUrlsResponse failed(Error error) {
        return builder().error(error).build();
    }
}
