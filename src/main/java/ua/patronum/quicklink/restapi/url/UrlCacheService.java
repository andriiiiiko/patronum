package ua.patronum.quicklink.restapi.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.data.entity.Url;

import java.util.Objects;

@Service
public class UrlCacheService {

    private static final String CACHE_NAME = "OriginalUrl";

    private final ConcurrentMapCacheManager cacheManager;
    private String shortUrl;

    @Autowired
    public UrlCacheService(ConcurrentMapCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Cacheable(value = CACHE_NAME, key = "#shortUrl")
    public Url getCachedUrl(String shortUrl) {
        Cache.ValueWrapper wrapper = Objects.requireNonNull(cacheManager.getCache(CACHE_NAME)).get(shortUrl);

        if (wrapper != null) {
            return (Url) wrapper.get();
        }
        return null;
    }

    @CacheEvict(value = CACHE_NAME, key = "#shortUrl")
    public void evictCache(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    @Cacheable(value = CACHE_NAME, key = "#shortUrl", unless = "#result == null")
    public void cacheUrl(String shortUrl, Url url) {
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME)).put(shortUrl, url);
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}