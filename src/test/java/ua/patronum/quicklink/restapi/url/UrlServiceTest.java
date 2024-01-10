package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.Test;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.repository.UrlRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class UrlServiceTest {

    @Test
    public void testRedirectOriginalUrlWithCachedUrl() {
        UrlCacheService urlCacheService = mock(UrlCacheService.class);
        UrlRepository urlRepository = mock(UrlRepository.class);

        UrlServiceImpl urlService = spy(new UrlServiceImpl(urlRepository, null, urlCacheService));

        String shortUrl = "https://example.com/short";
        RedirectRequest redirectRequest = new RedirectRequest();
        Url cachedUrl = new Url();
        cachedUrl.setShortUrl(shortUrl);
        cachedUrl.setOriginalUrl("https://example.com/original");

        when(urlCacheService.getCachedUrl(eq(shortUrl))).thenReturn(cachedUrl);

        RedirectResponse response = urlService.redirectOriginalUrl(redirectRequest);
        assertNotNull(response);


    }

    @Test
    public void testRedirectOriginalUrlWithNonCachedUrl() {
    }
}