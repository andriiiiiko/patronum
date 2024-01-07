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
                .id(1L)
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .dateCreated(now)
                .expirationDate(expirationDate)
                .visitCount(0)
                .user(new User())
                .build();

        url.setId(1L);
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

    @Test
    void testEquals() {
        LocalDateTime now = LocalDateTime.now();
        Url url1 = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .dateCreated(now)
                .user(new User())
                .build();

        Url url2 = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .dateCreated(now)
                .user(new User())
                .build();

        assertEquals(url1, url2);

        url2.setOriginalUrl("https://modified.example.com");

        assertNotEquals(url1, url2);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setOriginalUrl("https://example.com"); // Повертаємо оригінальну адресу для подальших перевірок

        url2.setShortUrl("https://modified.short.url");
        assertNotEquals(url1, url2);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setShortUrl("https://short.url/abc");

        url2.setDateCreated(now.minusDays(1));
        assertNotEquals(url1, url2);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setDateCreated(now);

        url2.setExpirationDate(now.plusDays(1));
        assertNotEquals(url1, url2);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setExpirationDate(null);

        url2.setVisitCount(42);
        assertNotEquals(url1, url2);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setVisitCount(0);

        User differentUser = User.builder()
                .username("differentUser")
                .password("password123")
                .enabled(true)
                .role("ROLE_USER")
                .build();

        url2.setUser(differentUser);
        assertNotEquals(url1, url2);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setUser(url1.getUser());

        assertEquals(url1, url2);
        assertEquals(url1.hashCode(), url2.hashCode());
    }

    @Test
    void testToString() {
        LocalDateTime now = LocalDateTime.now();
        url.setId(1L);
        url.setDateCreated(now);
        url.setExpirationDate(now.plusDays(30));
        url.setVisitCount(5);

        String expectedToString = "Url(" +
                "id=1, " +
                "originalUrl=https://example.com, " +
                "shortUrl=https://short.url/abc, " +
                "dateCreated=" + now + ", " +
                "expirationDate=" + now.plusDays(30) + ", " +
                "visitCount=5, " +
                "user=User(id=null, username=null, password=null, enabled=false, role=null, urls=[])" +
                ')';

        assertEquals(expectedToString, url.toString());
    }

    @Test
    void testHashCode() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30);

        Url url1 = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .dateCreated(now)
                .expirationDate(expirationDate)
                .visitCount(0)
                .user(new User())
                .build();

        Url url2 = Url.builder()
                .originalUrl("https://example.com")
                .shortUrl("https://short.url/abc")
                .dateCreated(now)
                .expirationDate(expirationDate)
                .visitCount(0)
                .user(new User())
                .build();

        assertEquals(url1.hashCode(), url2.hashCode());

        url2.setOriginalUrl("https://modified.example.com");
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setOriginalUrl("https://example.com");

        url2.setShortUrl("https://modified.short.url");
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setShortUrl("https://short.url/abc");

        url2.setDateCreated(now.minusDays(1));
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setDateCreated(now);

        url2.setExpirationDate(now.plusDays(1));
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setExpirationDate(null);

        url2.setVisitCount(42);
        assertNotEquals(url1.hashCode(), url2.hashCode());

        url2.setVisitCount(0);

        url2.setUser(new User());

        assertNotEquals(url1.hashCode(), url2.hashCode());
    }
}