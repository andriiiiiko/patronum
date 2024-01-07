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
       Url otherUrl = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .user(new User())
                .build();

        otherUrl.setDateCreated(url.getDateCreated());

        assertEquals(url, otherUrl);
        assertEquals(url.hashCode(), otherUrl.hashCode());
        assertNotEquals(url, null);
    }

    @Test
    void testFullConstructor() {
       Url fullUrl = new Url(
                1L,
                "https://example.com",
                "https://short.url/abc",
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(30),
                0,
                new User()
        );

        assertEquals(1L, fullUrl.getId());
        assertEquals("https://example.com", fullUrl.getOriginalUrl());
        assertEquals("https://short.url/abc", fullUrl.getShortUrl());
        assertNotNull(fullUrl.getDateCreated());
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
}
