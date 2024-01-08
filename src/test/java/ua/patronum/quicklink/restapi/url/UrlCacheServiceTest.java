package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import ua.patronum.quicklink.data.entity.Url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlCacheServiceTest {

    private static final String TEST_SHORT_URL = "testShortUrl";
    private static final String TEST_ORIGINAL_URL = "testOriginalUrl";

    @Mock
    private ConcurrentMapCacheManager cacheManager;

    @Mock
    private Cache cache;

    @InjectMocks
    private UrlCacheService urlCacheService;

    @Test
    void testGetCachedUrl() {
        Url cachedUrl = new Url();
        cachedUrl.setOriginalUrl(TEST_ORIGINAL_URL);

        when(cacheManager.getCache(anyString())).thenReturn(cache);
        when(cache.get(eq(TEST_SHORT_URL))).thenReturn(() -> cachedUrl);

        Url result = urlCacheService.getCachedUrl(TEST_SHORT_URL);

        assertNotNull(result);
        assertEquals(TEST_ORIGINAL_URL, result.getOriginalUrl());
    }

    @Test
    void testEvictCache() {
        urlCacheService.evictCache(TEST_SHORT_URL);

        assertEquals(TEST_SHORT_URL, urlCacheService.getShortUrl());
    }

    @Test
    void testCacheUrl() {
        Url url = new Url();
        url.setOriginalUrl(TEST_ORIGINAL_URL);

        when(cacheManager.getCache(anyString())).thenReturn(cache);

        urlCacheService.cacheUrl(TEST_SHORT_URL, url);

        verify(cache).put(eq(TEST_SHORT_URL), eq(url));
    }

    @Test
    void testCheckCacheError() {
        Error result = urlCacheService.checkCacheError("CACHE_ERROR");

        assertEquals(Error.CACHE_ERROR, result);
    }
}