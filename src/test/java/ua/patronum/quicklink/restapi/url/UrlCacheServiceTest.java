package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import ua.patronum.quicklink.data.entity.Url;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UrlCacheServiceTest {

    private UrlCacheService urlCacheService;
    private ConcurrentMapCacheManager cacheManager;

    @BeforeEach
    void setUp() {
        cacheManager = new ConcurrentMapCacheManager();
        urlCacheService = new UrlCacheService(cacheManager);
    }

    @Test
    void testGetCachedUrl() {
        Url url = new Url();
        String shortUrl = "testShortUrl";

        assertNotNull(cacheManager);
        assertNotNull(cacheManager.getCache("OriginalUrl"));
        Objects.requireNonNull(cacheManager.getCache("OriginalUrl")).put(shortUrl, url);

        assertNotNull(urlCacheService);
        assertNotNull(cacheManager);
        assertNotNull(cacheManager.getCache("OriginalUrl"));

        Object cachedValue = Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache("OriginalUrl")).get(shortUrl)).get();

        assertTrue(cachedValue instanceof Url);

        Url cachedUrl = (Url) cachedValue;

        assertNotNull(cachedUrl);
        assertEquals(url, cachedUrl);
    }
}