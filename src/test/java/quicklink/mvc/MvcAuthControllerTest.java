package quicklink.mvc;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import ua.patronum.quicklink.mvc.MvcAuthController;
import ua.patronum.quicklink.restapi.auth.AuthService;
import ua.patronum.quicklink.restapi.auth.RegistrationRequest;
import ua.patronum.quicklink.restapi.auth.RegistrationResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MvcAuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private Model model;

    @InjectMocks
    private MvcAuthController authController;

    public MvcAuthControllerTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testShowRegistrationPage() {
        String result = authController.showRegistrationPage();
        assertEquals("registration", result);
    }

    @Test
    void testRegisterSuccess() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("test","Test111","Test111");
        RegistrationResponse successResponse =RegistrationResponse.success();
        when(authService.register(registrationRequest)).thenReturn(successResponse);

        String result = authController.register(registrationRequest, model);

        assertEquals("redirect:/auth/login", result);
        verify(authService, times(1)).register(registrationRequest);
        verifyNoMoreInteractions(authService);
    }

    @Test
    void testRegisterError() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("test","test111","test111");

        RegistrationResponse errorResponse = RegistrationResponse.failed(RegistrationResponse.Error.INVALID_PASSWORD);
        when(authService.register(registrationRequest)).thenReturn(errorResponse);

        String result = authController.register(registrationRequest, model);

        assertEquals("registration", result);
        verify(authService, times(1)).register(registrationRequest);
        verify(model, times(1)).addAttribute(eq("error"), eq("INVALID_PASSWORD"));
        verifyNoMoreInteractions(authService, model);
    }

    @Test
    void testShowLoginPage() {
        String result = authController.showLoginPage();
        assertEquals("login", result);
    }
}