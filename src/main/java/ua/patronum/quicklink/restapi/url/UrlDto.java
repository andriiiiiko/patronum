package ua.patronum.quicklink.restapi.url;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UrlDto {

    private Long id;
    private String username;
    private String originalUrl;
    private String shortUrl;
    private LocalDateTime dateCreated;
    private LocalDateTime expirationDate;
    private int visitCount;
}
