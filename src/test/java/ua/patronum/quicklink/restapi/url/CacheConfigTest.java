package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = CacheConfig.class)
class CacheConfigTest {

    @Autowired
    private ConcurrentMapCacheManager cacheManager;

    @Test
    void testCacheManagerExists() {
        assertNotNull(cacheManager);

        Cache originalUrlCache = cacheManager.getCache("OriginalUrl");
        assertNotNull(originalUrlCache);
    }
}