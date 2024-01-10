package ua.patronum.quicklink.restapi.url;

import org.junit.jupiter.api.BeforeEach;
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
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @Mock
    private UserService userService;

    @Mock
    UrlCacheService cacheService;

    @InjectMocks
    private UrlServiceImpl urlService;

    private User user;
    private List<Url> mockUrls;
    private String shortUrl;
    private Url mockUrl;
    private Url urlToDelete;
    private Long urlIdToDelete;

    @BeforeEach
    void setUp() {
        user = new User(1L, "testUser", "Password2024", true,
                "ROLE_ADMIN", new HashSet<>());

        mockUrls = Arrays.asList(
                new Url(1L, "https://www.example.com/1", "abc123",
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user),
                new Url(2L, "https://www.example.com/2", "def456",
                        LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user)
        );
        shortUrl = "abc123";
        mockUrl = new Url(1L, "https://www.example.com/", shortUrl,
                LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, null);
        urlToDelete = new Url(urlIdToDelete, "https://www.example.com/delete", "delete123",
                LocalDateTime.now(), LocalDateTime.now().plusDays(30L), 0, user);
    }

    @Test
    void test_Create_Url_Success() {

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

        when(urlRepository.findAll()).thenReturn(mockUrls);
        GetAllUrlsResponse response = urlService.getAllUrls();
        List<UrlDto> urlDtos = response.getUrls();
        assertEquals(mockUrls.size(), urlDtos.size());
    }

    @Test
    void test_Get_All_User_Urls_Success() {

        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        when(urlRepository.findByUser(user)).thenReturn(mockUrls);

        GetAllUserUrlsResponse response = urlService.getAllUserUrls(user.getUsername());
        List<UrlDto> urlDtos = response.getUserUrls();
        assertEquals(mockUrls.size(), urlDtos.size());
    }

    @Test
    void test_Get_All_User_Active_Urls_Success() {

        when(userService.findByUsername(user.getUsername())).thenReturn(user);

        when(urlRepository.findByUser(user)).thenReturn(mockUrls);

        GetAllUserActiveUrlsResponse response = urlService.getAllUserActiveUrl(user.getUsername());
        List<UrlDto> urlDtos = response.getUserUrls();
        assertEquals(mockUrls.size(), urlDtos.size());
    }

    @Test
    void test_Get_All_Active_Urls_Success() {

        when(urlRepository.findAll()).thenReturn(mockUrls);

        GetAllActiveUrlsResponse response = urlService.getAllActiveUrls();

        List<UrlDto> urlDtos = response.getUrls();
        long activeUrlsCount = mockUrls.stream()
                .filter(url -> url.getExpirationDate() != null && url
                        .getExpirationDate()
                        .isAfter(LocalDateTime.now()))
                .count();
        assertEquals(activeUrlsCount, urlDtos.size());
    }

    @Test
    void test_Redirect_Original_Url_Success() {

        RedirectRequest request = new RedirectRequest();
        request.setShortUrl(shortUrl);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        RedirectResponse response = urlService.redirectOriginalUrl(request);
        assertEquals(RedirectResponse.success(mockUrl.getOriginalUrl()), response);
    }

    @Test
    void test_Redirect_Original_Url_Failed() {

        RedirectRequest request = new RedirectRequest();
        request.setShortUrl(shortUrl);
        mockUrl.setDateCreated(LocalDateTime.now().minusMonths(3L));
        mockUrl.setExpirationDate();

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        RedirectResponse response = urlService.redirectOriginalUrl(request);
        assertEquals(Error.TIME_NOT_PASSED, response.getError());
        verify(urlRepository, never()).save(any(Url.class));
    }

    @Test
    void test_Delete_By_Id_Success() {

        urlIdToDelete = 1L;

        when(urlRepository.findById(urlIdToDelete)).thenReturn(Optional.of(urlToDelete));

        DeleteUrlResponse response = urlService.deleteUrlById(user.getUsername(), urlIdToDelete);

        verify(urlRepository, times(1)).deleteById(urlIdToDelete);
        assertEquals(DeleteUrlResponse.success(), response);
    }

    @Test
    void test_Delete_By_Id_Failed() {

        urlIdToDelete = 10L;

        when(urlRepository.findById(urlIdToDelete)).thenReturn(Optional.of(urlToDelete));

        DeleteUrlResponse response = urlService.deleteUrlById(user.getUsername(), urlIdToDelete);

        verify(urlRepository, times(1)).deleteById(urlIdToDelete);
        assertEquals(DeleteUrlResponse.failed(Error.INVALID_ID), response);
    }

    @Test
    void test_Extension_Time_Response_Success() {

        ExtensionTimeRequest request = new ExtensionTimeRequest();
        request.setShortUrl(shortUrl);

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        ExtensionTimeResponse response = urlService.getExtensionTime(request);
        assertEquals(ExtensionTimeResponse.success(), response);
    }

    @Test
    void test_Extension_Time_Response_Failed() {

        ExtensionTimeRequest request = new ExtensionTimeRequest();
        request.setShortUrl(shortUrl);
        mockUrl.setDateCreated(LocalDateTime.now().minusDays(2L));
        mockUrl.setExpirationDate();

        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(Optional.of(mockUrl));

        ExtensionTimeResponse response = urlService.getExtensionTime(request);

        assertEquals(Error.TIME_NOT_PASSED, response.getError());
        verify(urlRepository, never()).save(any(Url.class));
    }
}