package ua.patronum.quicklink.mvc.home;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import ua.patronum.quicklink.restapi.url.CreateUrlRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MvcHomeControllerTest {

    @Mock
    private MvcHomeService service;
    @InjectMocks
    private MvcHomeController controller;

    @Test
    void showHomePageTest() {
        Model model = mock(Model.class);
        when(service.showHomePage(model)).thenReturn("homePage");

        String viewName = controller.showHomePage(model);

        assertEquals("homePage", viewName);
        verify(service, times(1)).showHomePage(model);
    }

    @Test
    void showAllActiveUrlTest() {
        Model model = mock(Model.class);
        when(service.showAllActiveUrl(model)).thenReturn("activeUrlsPage");

        String viewName = controller.showAllActiveUrl(model);

        assertEquals("activeUrlsPage", viewName);
        verify(service, times(1)).showAllActiveUrl(model);
    }

    @Test
    void showAllUserURLTest() {
        Model model = mock(Model.class);
        when(service.showAllUserURL(model)).thenReturn("userUrlsPage");

        String viewName = controller.showAllUserURL(model);

        assertEquals("userUrlsPage", viewName);
        verify(service, times(1)).showAllUserURL(model);
    }

    @Test
    void showAllUserActiveURLTest() {
        Model model = mock(Model.class);
        when(service.showAllUserActiveURL(model)).thenReturn("userActiveUrlsPage");

        String viewName = controller.showAllUserActiveURL(model);

        assertEquals("userActiveUrlsPage", viewName);
        verify(service, times(1)).showAllUserActiveURL(model);
    }

    @Test
    void createTest() {
        CreateUrlRequest request = new CreateUrlRequest();
        Model model = mock(Model.class);
        when(service.create(request, model)).thenReturn("createPage");

        String viewName = controller.create(request, model);

        assertEquals("createPage", viewName);
        verify(service, times(1)).create(request, model);
    }

    @Test
    void deleteTest() {
        Long id = 1L;
        when(service.delete(id)).thenReturn("deletePage");

        String viewName = controller.delete(id);

        assertEquals("deletePage", viewName);
        verify(service, times(1)).delete(id);
    }

    @Test
    void redirectTest() {
        Long id = 1L;
        when(service.redirect(id)).thenReturn("redirectPage");

        String viewName = controller.redirect(id);

        assertEquals("redirectPage", viewName);
        verify(service, times(1)).redirect(id);
    }

    @Test
    void getRedirectTest() {
        Model model = mock(Model.class);
        when(service.getRedirect(model)).thenReturn("getRedirectPage");

        String viewName = controller.getRedirect(model);

        assertEquals("getRedirectPage", viewName);
        verify(service, times(1)).getRedirect(model);
    }
}