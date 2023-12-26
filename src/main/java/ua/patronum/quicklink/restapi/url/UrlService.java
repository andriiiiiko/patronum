package ua.patronum.quicklink.restapi.url;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.restapi.auth.dto.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlService {

    private static final int SHORT_URL_LENGTH = 6;
    private static final String URL_PREFIX = "https://";
    private final Random random = new Random();
    private final UrlRepository urlRepository;
    private final UserService userService;
    private List<Url> userUrls;

    public GetAllUrlsResponse getAllUrls() {
        List<Url> allUrls = urlRepository.findAll();

        List<UrlDto> urlDtos = allUrls.stream()
                .map(url -> UrlDto.builder()
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .build())
                .collect(Collectors.toList());

        return GetAllUrlsResponse.success(urlDtos);
    }

    public GetAllUserUrlResponse getAllUserUrls(String username) {
        User user = userService.findByUsername(username);
        userUrls = urlRepository.findByUser(user);
        List<UrlDto> urlDtos = userUrls.stream()
                .map(url -> UrlDto.builder()
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .build())
                .collect(Collectors.toList());
        return GetAllUserUrlResponse.success(urlDtos);
    }

    public GetAllActiveUrlResponse getAllUserActiveUrl(String username) {
        User user = userService.findByUsername(username);
        userUrls = urlRepository.findByUser(user);

        List<UrlDto> urlDtos = userUrls.stream()
                .filter(url -> url.getExpirationDate() == null || url.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(url -> UrlDto.builder()
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .build())

                .collect(Collectors.toList());
        return GetAllActiveUrlResponse.success(urlDtos);
    }

    public CreateUrlResponse createUrl(String username, CreateUrlRequest request) {

        User user = userService.findByUsername(username);
        Optional<CreateUrlResponse.Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return CreateUrlResponse.failed(validationError.get());
        }

        String normalizeUrl = normalizeUrl(request);
        if (!isValidateUrl(normalizeUrl)) {
            return CreateUrlResponse.failed(CreateUrlResponse.Error.INVALID_OLD_VALID_URL);
        }

        Url url = Url.builder()
                .originalUrl(normalizeUrl)
                .shortUrl(generateShortUrl(request.getOriginalUrl()))
                .dateCreated(LocalDateTime.now())
                .visitCount(0)
                .user(user)
                .build();
        url.setExpirationDate();
        urlRepository.save(url);

        return CreateUrlResponse.success();
    }

    public DeleteUrlResponse deleteUrlById(Long id) {
        Optional<Url> optionalUrl = urlRepository.findById(id);
        if (optionalUrl.isEmpty()) {
            return DeleteUrlResponse.failed(DeleteUrlResponse.Error.INVALID_ID);
        }
        urlRepository.deleteById(id);
        return DeleteUrlResponse.success();
    }

    public String generateShortUrl(String originalUrl) {
        String afterHttps = originalUrl.substring(URL_PREFIX.length());
        StringBuilder randomLetters = new StringBuilder();
        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            int randomIndex = random.nextInt(afterHttps.length());
            char randomChar = afterHttps.charAt(randomIndex);
            randomLetters.append(randomChar);
        }

        return URL_PREFIX + randomLetters;
    }

    private Optional<CreateUrlResponse.Error> validateCreateFields(CreateUrlRequest request) {
        if (Objects.isNull(request.getOriginalUrl()) || request.getOriginalUrl().isEmpty()) {
            return Optional.of(CreateUrlResponse.Error.EMPTY_NEW_URL);
        }

        return Optional.empty();
    }

    private boolean isValidateUrl(String inputUrl) {
        int statusCode = getStatusCode(inputUrl);
        return 200 <= statusCode && statusCode <= 204;
    }

    private int getStatusCode(String inputUrl) {
        try {
            URL urlObject = new URL(inputUrl);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("HEAD");
            try (InputStream ignored = connection.getInputStream()) {
                return connection.getResponseCode();
            }
        } catch (IOException ignore) {
            return -1;
        }
    }

    private String normalizeUrl(CreateUrlRequest request) {
        try {
            new URL(request.getOriginalUrl());
        } catch (MalformedURLException ignored) {
            return URL_PREFIX + request.getOriginalUrl();
        }
        return request.getOriginalUrl();
    }

    public RedirectResponse redirectOriginalUrl(RedirectRequest request) {

        Optional<Url> byShortUrl = urlRepository.findByShortUrl(request.getShortUrl());
        if (byShortUrl.isEmpty()) {
            return RedirectResponse.failed(RedirectResponse.Error.INVALID_SHORT_URL);
        }

        Url url = byShortUrl.get();
        url.incrementVisitCount();
        urlRepository.save(url);

        return RedirectResponse.success(url.getOriginalUrl());
    }
}
