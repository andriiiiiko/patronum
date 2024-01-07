package ua.patronum.quicklink.data.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UrlTest {

    private Url url;

    @BeforeEach
    void setUp() {
        url = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .user(new User())
                .build();
    }

    @Test
    void testSetExpirationDate() {
        assertNull(url.getExpirationDate());

        url.setExpirationDate();

        assertNotNull(url.getExpirationDate());
    }

    @Test
    void testIncrementVisitCount() {
        assertEquals(0, url.getVisitCount());

        url.incrementVisitCount();

        assertEquals(1, url.getVisitCount());
    }

    @Test
    void testBuilder() {
        assertNotNull(url);
        assertNotNull(url.getDateCreated());
        assertEquals(0, url.getVisitCount());
        assertNull(url.getExpirationDate());
    }

    @Test
    void testDataAnnotation() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30);
        Url otherUrl = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .dateCreated(now)
                .expirationDate(expirationDate)
                .visitCount(0)
                .user(new User())
                .build();

        url.setDateCreated(now);
        url.setExpirationDate(expirationDate);
        url.setVisitCount(0);


        assertEquals(url, otherUrl);
        assertEquals(url.hashCode(), otherUrl.hashCode());
        assertNotEquals(url, null);
    }

    @Test
    void testFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Url fullUrl = new Url(
                1L,
                "https://example.com",
                "https://short.url/abc",
                now,
                now.plusDays(30),
                0,
                new User()
        );

        assertEquals(1L, fullUrl.getId());
        assertEquals("https://example.com", fullUrl.getOriginalUrl());
        assertEquals("https://short.url/abc", fullUrl.getShortUrl());
        assertEquals(now, fullUrl.getDateCreated());
        assertNotNull(fullUrl.getExpirationDate());
        assertEquals(0, fullUrl.getVisitCount());
        assertNotNull(fullUrl.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        LocalDateTime expectedDate = LocalDateTime.now();
        User expectedUser = new User();

        Url emptyUrl = new Url();
        emptyUrl.setOriginalUrl("https://example.com");
        emptyUrl.setShortUrl("https://short.url/abc");
        emptyUrl.setDateCreated(expectedDate);
        emptyUrl.setUser(expectedUser);

        assertNotNull(emptyUrl);
        assertNull(emptyUrl.getId());
        assertEquals("https://example.com", emptyUrl.getOriginalUrl());
        assertEquals("https://short.url/abc", emptyUrl.getShortUrl());
        assertEquals(expectedDate, emptyUrl.getDateCreated());
        assertNull(emptyUrl.getExpirationDate());
        assertEquals(0, emptyUrl.getVisitCount());

        assertNotNull(emptyUrl.getUser());
        assertEquals(expectedUser, emptyUrl.getUser());
    }

    @Test
    void testSetters() {
        Long id = 456L;
        String originalUrl = "https://newexample.com";
        String shortUrl = "https://newshort.url/xyz";
        LocalDateTime dateCreated = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(15);
        int visitCount = 99;
        User user = new User();

        url.setId(id);
        url.setOriginalUrl(originalUrl);
        url.setShortUrl(shortUrl);
        url.setDateCreated(dateCreated);
        url.setExpirationDate(expirationDate);
        url.setVisitCount(visitCount);
        url.setUser(user);

        assertEquals(id, url.getId());
        assertEquals(originalUrl, url.getOriginalUrl());
        assertEquals(shortUrl, url.getShortUrl());
        assertEquals(dateCreated, url.getDateCreated());
        assertEquals(expirationDate, url.getExpirationDate());
        assertEquals(visitCount, url.getVisitCount());
        assertEquals(user, url.getUser());
    }
}

