package ua.patronum.quicklink;

import org.junit.jupiter.api.Test;
import ua.patronum.quicklink.restapi.url.CreateUrlRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateUrlRequestTest {

    @Test
    void whenSetOriginalUrl_thenGetOriginalUrl() {
        // given
        CreateUrlRequest request = new CreateUrlRequest();
        String givenUrl = "https://quicklink.sbs";

        // when
        request.setOriginalUrl(givenUrl);

        // then
        assertEquals(givenUrl, request.getOriginalUrl());
    }
}