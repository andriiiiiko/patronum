package ua.patronum.quicklink.mvc.home;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.mvc.MvcError;
import ua.patronum.quicklink.restapi.url.Error;
import ua.patronum.quicklink.restapi.url.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class MvcHomeServiceTest {

    private final UrlRepository repository = mock(UrlRepository.class);
    @Mock
    private UrlServiceImpl urlService;
    @Mock
    private Model model;
    @InjectMocks
    private MvcHomeService mvcHomeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showHomePageTest() {
        when(urlService.getAllUrls())
                .thenReturn(GetAllUrlsResponse.success(Collections.emptyList()));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        String result = mvcHomeService.showHomePage(model);

        assertEquals("home-authorized", result);

        verify(model).addAttribute(eq("urlList"), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void showAllActiveURLTest() {
        when(urlService.getAllActiveUrls())
                .thenReturn(GetAllActiveUrlsResponse.success(Collections.emptyList()));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        String result = mvcHomeService.showAllActiveUrl(model);

        assertEquals("home-authorized", result);

        verify(model).addAttribute(eq("urlList"), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void showAllUserURL() {
        when(urlService.getAllUserUrls("testUser"))
                .thenReturn(GetAllUserUrlsResponse.success(Collections.emptyList()));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        String result = mvcHomeService.showAllUserURL(model);

        assertEquals("home-authorized", result);

        verify(model).addAttribute(eq("urlList"), anyList());
        verify(model).addAttribute(eq("isUserListPage"), eq(true));
        verifyNoMoreInteractions(model);
    }

    @Test
    void showAllUserActiveURL() {
        when(urlService.getAllUserActiveUrl("testUser"))
                .thenReturn(GetAllUserActiveUrlsResponse.success(Collections.emptyList()));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        String result = mvcHomeService.showAllUserActiveURL(model);

        assertEquals("home-authorized", result);

        verify(model).addAttribute(eq("urlList"), anyList());
        verify(model).addAttribute(eq("isUserListPage"), eq(true));
        verifyNoMoreInteractions(model);
    }


    @Test
    void createTest() {
        CreateUrlRequest createUrlRequest = new CreateUrlRequest();

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(urlService.createUrl(eq("testUser"), any()))
                .thenReturn(CreateUrlResponse.success());

        String result = mvcHomeService.create(createUrlRequest, model);

        assertEquals("redirect:/mvc/home", result);

        verify(urlService).createUrl(eq("testUser"), eq(createUrlRequest));
        verifyNoMoreInteractions(urlService, model);
    }

    @Test
    void createTestWithError() {
        CreateUrlRequest createUrlRequest = new CreateUrlRequest();

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(urlService.createUrl(eq("testUser"), any()))
                .thenReturn(CreateUrlResponse.failed(Error.EXPIRED_URL));

        List<UrlDto> urls = Collections.emptyList();
        when(urlService.getAllUrls()).thenReturn(GetAllUrlsResponse.success(urls));

        String result = mvcHomeService.create(createUrlRequest, model);

        assertEquals("home-authorized", result);

        verify(urlService).createUrl("testUser", createUrlRequest);
        verify(model).addAttribute("error", MvcError.INVALID_URL.getErrorMessage());
        verify(model).addAttribute("urlList", urls);
    }

    @Test
    void deleteTest() {
        doAnswer(invocation -> null).when(urlService).deleteUrlById(anyString(), anyLong());

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken("testUser", "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        String result = mvcHomeService.delete(1L);

        assertEquals("redirect:/mvc/home/user/list", result);

        verify(urlService).deleteUrlById(eq("testUser"), eq(1L));
        verifyNoMoreInteractions(urlService);
    }

    @Test
    void redirectWithExistingUrl() {
        Long urlId = 1L;
        String shortUrl = "short-url";
        String originalUrl = "http://example.com";
        RedirectResponse redirectResponse = RedirectResponse.success(originalUrl);
        Url url = new Url();
        url.setId(urlId);
        url.setShortUrl(shortUrl);
        url.setOriginalUrl(originalUrl);

        when(repository.findById(urlId)).thenReturn(Optional.of(url));
        when(urlService.redirectOriginalUrl(any(RedirectRequest.class)))
                .thenReturn(redirectResponse);

        String result = mvcHomeService.redirect(urlId);

        assertEquals("redirect:" + originalUrl, result);

        verify(repository).findById(urlId);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void redirectWithNonExistingUrl() {
        Long nonExistingUrlId = 2L;

        when(repository.findById(nonExistingUrlId)).thenReturn(Optional.empty());

        String result = mvcHomeService.redirect(nonExistingUrlId);

        assertEquals("redirect:/mvc/home", result);

        verify(repository).findById(nonExistingUrlId);
    }

    @Test
    void getRedirectTest() {
        String username = "testUser";
        List<UrlDto> urls = Collections.emptyList();
        when(urlService.getAllUrls()).thenReturn(GetAllUrlsResponse.success(urls));

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(username, "password");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Act
        String result = mvcHomeService.getRedirect(model);

        // Assert
        assertEquals("home-authorized", result);

        // Verify model interactions
        verify(model).addAttribute("error", MvcError.EXPIRED_URL.getErrorMessage());
        verify(model).addAttribute("urlList", urls);
    }
}