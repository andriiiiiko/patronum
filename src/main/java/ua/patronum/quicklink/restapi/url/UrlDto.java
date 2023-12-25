package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UrlDto {

        private String originalUrl;
        private String shortUrl;
}
