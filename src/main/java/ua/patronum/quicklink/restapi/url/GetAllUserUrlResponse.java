package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
import ua.patronum.quicklink.data.entity.Url;

import java.util.List;

@Data
@Builder
public class GetAllUserUrlResponse {
    private List<Url> urls;

    public enum Error {
        OK,
        EMPTY_LIST,
    }

    private GetAllUserUrlResponse.Error error;

    public static GetAllUserUrlResponse success(List<Url> urls) {
        return builder().urls(urls).error(GetAllUserUrlResponse.Error.OK).build();
    }

    public static GetAllUserUrlResponse failed(GetAllUserUrlResponse.Error error) {
        return builder().error(error).build();
    }
}
