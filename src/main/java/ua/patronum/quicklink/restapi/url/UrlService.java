package ua.patronum.quicklink.restapi.url;

public interface UrlService {
    CreateUrlResponse createUrl(String username, CreateUrlRequest request);
    DeleteUrlResponse deleteUrlById(Long id);
    GetAllUserActiveUrlsResponse getAllUserActiveUrl(String username);
    GetAllUrlsResponse getAllUrls();
    GetAllUserUrlsResponse getAllUserUrls(String username);
    RedirectResponse redirectOriginalUrl(RedirectRequest request);
    GetAllActiveUrlsResponse getAllActiveUrls();
}
