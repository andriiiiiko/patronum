package ua.patronum.quicklink.restapi.url;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.restapi.auth.dto.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UserService userService;
    private List<Url> allUrls;

    @Transactional
    public GetAllUrlsResponse getAllUrls() {
        allUrls = urlRepository.findAll();
        if (allUrls.isEmpty()) {
            return GetAllUrlsResponse.failed(GetAllUrlsResponse.Error.EMPTY_LIST);
        }

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
        List<Url> userUrls = urlRepository.findByUser(user);
        if (userUrls.isEmpty()) {
            return GetAllUserUrlResponse.failed(GetAllUserUrlResponse.Error.EMPTY_LIST);
        }
        return GetAllUserUrlResponse.success(userUrls);
    }

    public GetAllActiveUrlResponse getAllActiveUrlResponse() {
        List<Url> activeUrls = urlRepository.findByExpirationDateAfter(LocalDateTime.now());
        if (activeUrls.isEmpty()) {
            return GetAllActiveUrlResponse.failed(GetAllActiveUrlResponse.Error.EMPTY_LIST);
        }
        return GetAllActiveUrlResponse.success(activeUrls);
    }

    public CreateUrlResponse createUrl(String username, CreateUrlRequest request) {

        User user = userService.findByUsername(username);
        Optional<CreateUrlResponse.Error> validationError = validateCreateFields(request);

        if (validationError.isPresent()) {
            return CreateUrlResponse.failed(validationError.get());
        }

        Url url = Url.builder()
                .originalUrl(request.getOriginalUrl())
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
        String afterHttps = originalUrl.substring("https://".length());

        Random random = new Random();
        StringBuilder randomLetters = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(afterHttps.length());
            char randomChar = afterHttps.charAt(randomIndex);
            randomLetters.append(randomChar);
        }

        return "https://" + randomLetters;
    }

    private Optional<CreateUrlResponse.Error> validateCreateFields(CreateUrlRequest request) {
        if (Objects.isNull(request.getOriginalUrl()) || request.getOriginalUrl().isEmpty()) {
            return Optional.of(CreateUrlResponse.Error.EMPTY_NEW_URL);
        }

        return Optional.empty();
    }
}
