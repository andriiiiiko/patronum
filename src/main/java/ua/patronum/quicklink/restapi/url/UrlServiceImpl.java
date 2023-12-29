package ua.patronum.quicklink.restapi.url;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.restapi.auth.dto.service.UserService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

    private static final int SHORT_URL_LENGTH = 8;
    private static final String URL_PREFIX = "https://";

    private final UrlRepository urlRepository;
    private final UserService userService;
    private List<Url> userUrls;

    @Override
    public GetAllUrlsResponse getAllUrls() {
        List<Url> allUrls = urlRepository.findAll();
        List<UrlDto> urlDtos = allUrls.stream()
                .map(url -> UrlDto.builder()
                        .id(url.getId())
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .dateCreated(url.getDateCreated())
                        .expirationDate(url.getExpirationDate())
                        .visitCount(url.getVisitCount())
                        .build())
                .collect(Collectors.toList());

        return GetAllUrlsResponse.success(urlDtos);
    }

    @Override
    public GetAllUserUrlsResponse getAllUserUrls(String username) {
        User user = userService.findByUsername(username);
        userUrls = urlRepository.findByUser(user);
        List<UrlDto> urlDtos = userUrls.stream()
                .map(url -> UrlDto.builder()
                        .id(url.getId())
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .dateCreated(url.getDateCreated())
                        .expirationDate(url.getExpirationDate())
                        .visitCount(url.getVisitCount())
                        .build())
                .collect(Collectors.toList());
        return GetAllUserUrlsResponse.success(urlDtos);
    }

    @Override
    public GetAllUserActiveUrlsResponse getAllUserActiveUrl(String username) {
        User user = userService.findByUsername(username);
        userUrls = urlRepository.findByUser(user);

        List<UrlDto> urlDtos = userUrls.stream()
                .filter(url -> url.getExpirationDate() == null || url.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(url -> UrlDto.builder()
                        .id(url.getId())
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .dateCreated(url.getDateCreated())
                        .expirationDate(url.getExpirationDate())
                        .visitCount(url.getVisitCount())
                        .build())

                .collect(Collectors.toList());
        return GetAllUserActiveUrlsResponse.success(urlDtos);
    }

    @Override
    public CreateUrlResponse createUrl(String username, CreateUrlRequest request) {

        User user = userService.findByUsername(username);
        Optional<Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return CreateUrlResponse.failed(validationError.get());
        }

        String normalizeUrl = normalizeUrl(request);
        if (!isValidUrl(normalizeUrl)) {
            return CreateUrlResponse.failed(Error.INVALID_OLD_VALID_URL);
        }

        Url url = Url.builder()
                .originalUrl(normalizeUrl)
                .shortUrl(generateShortUrl(SHORT_URL_LENGTH))
                .dateCreated(LocalDateTime.now())
                .visitCount(0)
                .user(user)
                .build();
        url.setExpirationDate();
        urlRepository.save(url);

        return CreateUrlResponse.success();
    }

    @Override
    public DeleteUrlResponse deleteUrlById(String username, Long id) {
        Optional<Url> optionalUrl = urlRepository.findById(id);
        if (optionalUrl.isEmpty()) {
            return DeleteUrlResponse.failed(Error.INVALID_ID);
        }
        Url url = optionalUrl.get();

        if (!url.getUser().getUsername().equals(username)) {
            return DeleteUrlResponse.failed(Error.INVALID_ACCESS);
        }
        urlRepository.deleteById(id);
        return DeleteUrlResponse.success();
    }

    public String generateShortUrl(int length) {
        return URL_PREFIX + UUID.randomUUID().toString().subSequence(0, length);
    }

    private Optional<Error> validateCreateFields(CreateUrlRequest request) {
        if (Objects.isNull(request.getOriginalUrl()) || request.getOriginalUrl().isEmpty()) {
            return Optional.of(Error.EMPTY_NEW_URL);
        }

        return Optional.empty();
    }

    private boolean isValidUrl(String inputUrl) {
        int statusCode = getStatusCode(inputUrl);
        return 200 <= statusCode && statusCode <= 204;
    }

    private int getStatusCode(String inputUrl) {
        try {
            URL urlObject = new URL(inputUrl);
            HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
            connection.setRequestMethod("HEAD");
            return connection.getResponseCode();
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

    @Override
    public RedirectResponse redirectOriginalUrl(RedirectRequest request) {
        return urlRepository.findByShortUrl(request.getShortUrl())
                .map(url -> {
                    url.incrementVisitCount();
                    urlRepository.save(url);
                    return RedirectResponse.success(url.getOriginalUrl());
                })
                .orElse(RedirectResponse.failed(Error.INVALID_SHORT_URL));
    }

    @Override
    public GetAllActiveUrlsResponse getAllActiveUrls() {
        List<Url> allUrls = urlRepository.findAll();
        List<UrlDto> urlDtos = allUrls.stream()
                .filter(url -> url.getExpirationDate() == null || url.getExpirationDate().isAfter(LocalDateTime.now()))
                .map(url -> UrlDto.builder()
                        .id(url.getId())
                        .originalUrl(url.getOriginalUrl())
                        .shortUrl(url.getShortUrl())
                        .dateCreated(url.getDateCreated())
                        .expirationDate(url.getExpirationDate())
                        .visitCount(url.getVisitCount())
                        .build())

                .collect(Collectors.toList());
        return GetAllActiveUrlsResponse.success(urlDtos);
    }
}
