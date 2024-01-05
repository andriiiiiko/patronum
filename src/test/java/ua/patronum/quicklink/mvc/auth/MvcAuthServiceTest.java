package ua.patronum.quicklink.mvc.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import ua.patronum.quicklink.restapi.auth.AuthService;
import ua.patronum.quicklink.restapi.auth.RegistrationRequest;
import ua.patronum.quicklink.restapi.auth.RegistrationResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MvcAuthServiceTest {

    @Mock
    private AuthService authService;
    @InjectMocks
    private MvcAuthService mvcAuthService;

    @Test
    void registrationShouldRedirectOnSuccessfulRegistration() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("Test", "Test111", "Test111");
        Model model = mock(Model.class);
        RegistrationResponse successfulResponse = RegistrationResponse.success();
        when(authService.register(registrationRequest)).thenReturn(successfulResponse);

        String result = mvcAuthService.registration(model, registrationRequest);

        verify(authService).register(registrationRequest);
        verifyNoInteractions(model);
        assertEquals("redirect:/mvc/auth/login", result);
    }

    @Test
    void registrationShouldAddErrorMessageForUserAlreadyExists() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("testMVC2", "Test111", "Test111");
        Model model = mock(Model.class);
        RegistrationResponse userExistsResponse =
                RegistrationResponse.failed(RegistrationResponse.Error.USER_ALREADY_EXISTS);
        when(authService.register(registrationRequest)).thenReturn(userExistsResponse);

        String result = mvcAuthService.registration(model, registrationRequest);

        verify(authService).register(registrationRequest);
        verify(model).addAttribute("error", "User already exists");
        assertEquals("registration", result);
    }

    @Test
    void registrationShouldAddErrorMessageForInvalidConfirmPassword() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("Test", "Test111", "Test");
        Model model = mock(Model.class);
        RegistrationResponse invalidConfirmPasswordResponse =
                RegistrationResponse.failed(RegistrationResponse.Error.INVALID_CONFIRM_PASSWORD);
        when(authService.register(registrationRequest)).thenReturn(invalidConfirmPasswordResponse);

        String result = mvcAuthService.registration(model, registrationRequest);

        verify(authService).register(registrationRequest);
        verify(model).addAttribute("error", "Invalid confirm password");
        assertEquals("registration", result);
    }

    @Test
    void registrationShouldAddErrorMessageForInvalidPassword() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                ("Test", "test111", "test111");
        Model model = mock(Model.class);
        RegistrationResponse invalidPasswordResponse =
                RegistrationResponse.failed(RegistrationResponse.Error.INVALID_PASSWORD);
        when(authService.register(registrationRequest)).thenReturn(invalidPasswordResponse);

        String result = mvcAuthService.registration(model, registrationRequest);

        verify(authService).register(registrationRequest);
        verify(model).addAttribute("error", "Invalid password");
        assertEquals("registration", result);
    }

    @Test
    void registrationShouldAddErrorMessageForInvalidUsername() {
        RegistrationRequest registrationRequest = new RegistrationRequest
                (null, "Test111", "Test");
        Model model = mock(Model.class);
        RegistrationResponse invalidUsernameResponse =
                RegistrationResponse.failed(RegistrationResponse.Error.INVALID_USERNAME);
        when(authService.register(registrationRequest)).thenReturn(invalidUsernameResponse);

        String result = mvcAuthService.registration(model, registrationRequest);

        verify(authService).register(registrationRequest);
        verify(model).addAttribute("error", "Invalid username");
        assertEquals("registration", result);
    }
}