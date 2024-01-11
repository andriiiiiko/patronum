package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.support.SimpleValueWrapper;
import ua.patronum.quicklink.data.entity.Url;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class UrlCacheServiceTest {

    @Mock
    private ConcurrentMapCacheManager cacheManager;

    @InjectMocks
    private UrlCacheService urlCacheService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCachedUrl() {
        String shortUrl = "abc";
        Url cachedUrl = Url.builder()
                .originalUrl("http://example.com")
                .shortUrl(shortUrl)
                .dateCreated(LocalDateTime.now())
                .visitCount(0)
                .user(null)
                .build();

        Cache mockCache = Mockito.mock(Cache.class);
        when(cacheManager.getCache(UrlCacheService.getCACHE_NAME())).thenReturn(mockCache);

        when(mockCache.get(eq(shortUrl))).thenReturn(new SimpleValueWrapper(cachedUrl));

        Url result = urlCacheService.getCachedUrl(shortUrl);

        assertNotNull(result);
        assertEquals(cachedUrl, result);
    }

    @Test
    void testGetCachedUrlCacheError() {
        String shortUrl = "abc";
        Url cachedUrl = Url.builder()
                .originalUrl(UrlCacheService.getCACHE_ERROR())
                .shortUrl(shortUrl)
                .dateCreated(LocalDateTime.now())
                .visitCount(0)
                .user(null)
                .build();

        Cache mockCache = Mockito.mock(Cache.class);
        when(cacheManager.getCache(UrlCacheService.getCACHE_NAME())).thenReturn(mockCache);

        when(mockCache.get(eq(shortUrl))).thenReturn(new SimpleValueWrapper(cachedUrl));

        assertThrows(RuntimeException.class, () -> urlCacheService.getCachedUrl(shortUrl));
    }

    @Test
    void testEvictCache() {
        String shortUrl = "abc";

        urlCacheService.evictCache(shortUrl);

        assertEquals(shortUrl, urlCacheService.getShortUrl());
    }

    @Test
    void testCacheUrl() {
        String shortUrl = "abc";
        Url url = Url.builder()
                .originalUrl("http://example.com")
                .shortUrl(shortUrl)
                .dateCreated(LocalDateTime.now())
                .visitCount(0)
                .user(null)
                .build();

        Cache mockCache = Mockito.mock(Cache.class);
        when(cacheManager.getCache(UrlCacheService.getCACHE_NAME())).thenReturn(mockCache);

        ArgumentCaptor<String> shortUrlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Url> urlCaptor = ArgumentCaptor.forClass(Url.class);

        urlCacheService.cacheUrl(shortUrl, url);

        Mockito.verify(mockCache).put(shortUrlCaptor.capture(), urlCaptor.capture());

        assertEquals(shortUrl, shortUrlCaptor.getValue());
        assertEquals(url, urlCaptor.getValue());
    }

    @Test
    void testCheckCacheError() {
        Error result = urlCacheService.checkCacheError(UrlCacheService.getCACHE_ERROR());

        assertEquals(Error.CACHE_ERROR, result);
    }
}
