package ua.patronum.quicklink.restapi.url;

import lombok.Data;

@Data
public abstract class Response {

    private Error error;

    public static <T extends Response> T success(T response) {
        response.setError(Error.OK);
        return response;
    }

    public static <T extends Response> T failed(T response, Error error) {
        response.setError(error);
        return response;
    }
}
