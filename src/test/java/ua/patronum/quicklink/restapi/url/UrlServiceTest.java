package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.restapi.auth.UserService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UrlServiceImpl urlService;

    @Test
    void test_Create_Url_Success() {

        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        String originalUrl = "https://www.iloveimg.com/";
        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl(originalUrl);

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        CreateUrlResponse response = urlService.createUrl(user.getUsername(), request);
        verify(urlRepository, times(1)).save(any(Url.class));
        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("Password2024", user.getPassword());
        assertTrue(user.isEnabled());
        assertEquals("ROLE_ADMIN", user.getRole());
        assertEquals(CreateUrlResponse.success(), response);
    }

    @Test
    void test_Create_Url_Failed() {

        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        String originalUrl = "";
        CreateUrlRequest request = new CreateUrlRequest();
        request.setOriginalUrl(originalUrl);

        when(userService.findByUsername(user.getUsername())).thenReturn(user);
        CreateUrlResponse response = urlService.createUrl(user.getUsername(), request);
        verify(urlRepository, never()).save(any(Url.class));
        assertEquals(CreateUrlResponse.failed(Error.EMPTY_NEW_URL), response);
    }

    @Test
    void test_Get_All_Urls_Success() {

        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        List<Url> mockUrls = Arrays.asList(
                new Url(1L, "https://www.example.com/1", "abc123", LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user),
                new Url(2L, "https://www.example.com/2", "def456", LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user)
        );

        when(urlRepository.findAll()).thenReturn(mockUrls);
        GetAllUrlsResponse response = urlService.getAllUrls();
        List<UrlDto> urlDtos = response.getUrls();
        assertEquals(mockUrls.size(), urlDtos.size());
    }

    @Test
    void test_Get_All_User_Urls_Success() {

        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        List<Url> userUrls = Arrays.asList(
                new Url(1L, "https://www.example.com/1", "abc123", LocalDateTime.now(), null, 0, user),
                new Url(2L, "https://www.example.com/2", "def456", LocalDateTime.now(), null, 0, user)
        );

        when(urlRepository.findByUser(user)).thenReturn(userUrls);

        GetAllUserUrlsResponse response = urlService.getAllUserUrls(user.getUsername());
        List<UrlDto> urlDtos = response.getUserUrls();
        assertEquals(userUrls.size(), urlDtos.size());
    }

    @Test
    void test_Get_All_User_Active_Urls_Success() {

        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        List<Url> userActiveUrls = Arrays.asList(
                new Url(1L, "https://www.example.com/1", "abc123", LocalDateTime.now(), null, 0, user),
                new Url(2L, "https://www.example.com/2", "def456", LocalDateTime.now(), null, 0, user)
        );

        when(urlRepository.findByUser(user)).thenReturn(userActiveUrls);

        GetAllUserActiveUrlsResponse response = urlService.getAllUserActiveUrl(user.getUsername());
        List<UrlDto> urlDtos = response.getUserUrls();
        assertEquals(userActiveUrls.size(), urlDtos.size());
    }

    @Test
    void test_Get_All_Active_Urls_Success() {

        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        List<Url> mockUrls = Arrays.asList(
                new Url(1L, "https://www.example.com/1", "abc123", LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user),
                new Url(2L, "https://www.example.com/2", "def456", LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user)
        );

        when(urlRepository.findAll()).thenReturn(mockUrls);

        GetAllActiveUrlsResponse response = urlService.getAllActiveUrls();

        List<UrlDto> urlDtos = response.getUrls();
        long activeUrlsCount = mockUrls.stream()
                .filter(url -> url.getExpirationDate() != null && url.getExpirationDate().isAfter(LocalDateTime.now()))
                .count();
        assertEquals(activeUrlsCount, urlDtos.size());
    }

    @Test
    void test_Redirect_Original_Url_Success() {

        String shortUrl = "abc123";
        RedirectRequest request = new RedirectRequest();
        request.setShortUrl(shortUrl);
        Url mockUrl = new Url(1L, "https://www.example.com/", shortUrl, LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, null);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        RedirectResponse response = urlService.redirectOriginalUrl(request);
        assertEquals(RedirectResponse.success(mockUrl.getOriginalUrl()), response);
    }

    @Test
    void test_Redirect_Original_Url_Failed() {

        String shortUrl = "abc123";
        RedirectRequest request = new RedirectRequest();
        request.setShortUrl(shortUrl);
        Url mockUrl = new Url(1L, "https://www.example.com/", shortUrl, LocalDateTime.now().minusMonths(3L), LocalDateTime.now().minusMonths(3L).plusDays(30L), 0, null);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        RedirectResponse response = urlService.redirectOriginalUrl(request);
        assertEquals(Error.TIME_NOT_PASSED, response.getError());
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void test_Delete_By_Id_Success() {

        Long urlIdToDelete = 1L;
        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        Url urlToDelete = new Url(urlIdToDelete, "https://www.example.com/delete", "delete123", LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user);

        when(urlRepository.findById(urlIdToDelete)).thenReturn(Optional.of(urlToDelete));

        DeleteUrlResponse response = urlService.deleteUrlById(user.getUsername(), urlIdToDelete);

        verify(urlRepository, times(1)).deleteById(urlIdToDelete);
        assertEquals(DeleteUrlResponse.success(), response);
    }

    @Test
    void test_Delete_By_Id_Failed() {

        Long urlIdToDelete = 10L;
        User user = new User(1L, "testUser", "Password2024", true, "ROLE_ADMIN", new HashSet<>());
        Url urlToDelete = new Url(1L, "https://www.example.com/delete", "delete123", LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user);

        when(urlRepository.findById(urlIdToDelete)).thenReturn(Optional.of(urlToDelete));

        DeleteUrlResponse response = urlService.deleteUrlById(user.getUsername(), urlIdToDelete);

        verify(urlRepository, times(1)).deleteById(urlIdToDelete);
        assertEquals(DeleteUrlResponse.failed(Error.INVALID_ID), response);
    }

    @Test
    void test_Extension_Time_Response_Success() {

        String shortUrl = "abc123";
        ExtensionTimeRequest request = new ExtensionTimeRequest();
        request.setShortUrl(shortUrl);
        Url mockUrl = new Url(1L, "https://www.example.com/", shortUrl, LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, null);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        ExtensionTimeResponse response = urlService.getExtensionTime(request);
        assertEquals(ExtensionTimeResponse.success(), response);
    }

    @Test
    void test_Extension_Time_Response_Failed() {

        String shortUrl = "abc123";
        ExtensionTimeRequest request = new ExtensionTimeRequest();
        request.setShortUrl(shortUrl);
        Url mockUrl = new Url(1L, "https://www.example.com/", shortUrl, LocalDateTime.now().minusDays(2L), LocalDateTime.now().minusDays(2L).plusDays(30L), 0, null);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        ExtensionTimeResponse response = urlService.getExtensionTime(request);

        assertEquals(Error.TIME_NOT_PASSED, response.getError());
        verify(urlRepository, never()).save(any(Url.class));
    }
}
