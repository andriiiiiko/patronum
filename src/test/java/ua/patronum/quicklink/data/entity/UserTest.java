package ua.patronum.quicklink.data.entity;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testNoArgsConstructor() {
        User emptyUser = new User();

        assertNotNull(emptyUser);
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getUsername());
        assertNull(emptyUser.getPassword());
        assertFalse(emptyUser.isEnabled());
        assertNull(emptyUser.getRole());
        assertNotNull(emptyUser.getUrls());
        assertTrue(emptyUser.getUrls().isEmpty());
    }

    @Test
    void testBuilder() {
        Set<Url> expectedUrls = new HashSet<>(); // Set up any expected URLs

        User user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password123")
                .enabled(true)
                .role("ROLE_USER")
                .urls(expectedUrls)
                .build();

        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isEnabled());
        assertEquals("ROLE_USER", user.getRole());
        assertNotNull(user.getUrls());
        assertEquals(expectedUrls, user.getUrls());
    }

    @Test
    void testBuilderDefaultUrls() {
        User user = User.builder()
                .id(1L)
                .username("testUser")
                .password("password123")
                .enabled(true)
                .role("ROLE_USER")
                .build();

        assertNotNull(user.getUrls());
        assertTrue(user.getUrls().isEmpty());
    }

    @Test
    void testInequality() {
        Set<Url> urls1 = new HashSet<>();
        urls1.add(Url.builder().originalUrl("https://example1.com").shortUrl("https://short1.url").build());
        User user1 = new User(1L, "user1", "password1", true, "ROLE_USER", urls1);

        Set<Url> urls2 = new HashSet<>();
        urls2.add(Url.builder().originalUrl("https://example2.com").shortUrl("https://short2.url").build());
        User user2 = new User(2L, "user2", "password2", false, "ROLE_ADMIN", urls2);

        assertNotEquals(user1, user2);
    }
}
