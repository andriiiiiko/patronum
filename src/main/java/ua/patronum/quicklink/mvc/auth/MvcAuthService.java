package ua.patronum.quicklink.mvc.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.patronum.quicklink.restapi.auth.AuthService;
import ua.patronum.quicklink.restapi.auth.RegistrationRequest;
import ua.patronum.quicklink.restapi.auth.RegistrationResponse;

@RequiredArgsConstructor
@Service
public class MvcAuthService {


    private static final String BASE_ATTRIBUTE = "error";
    private final AuthService service;

    public String registration(Model model, RegistrationRequest registrationRequest) {
        RegistrationResponse response = service.register(registrationRequest);

        switch (response.getError()) {
            case OK:
                return "redirect:/mvc/auth/login";

            case USER_ALREADY_EXISTS:
                model.addAttribute(BASE_ATTRIBUTE, RegistrationError.USER_ALREADY_EXISTS.getErrorMessage());
                break;

            case INVALID_CONFIRM_PASSWORD:
                model.addAttribute(BASE_ATTRIBUTE, RegistrationError.INVALID_CONFIRM_PASSWORD.getErrorMessage());
                break;

            case INVALID_PASSWORD:
                model.addAttribute(BASE_ATTRIBUTE, RegistrationError.INVALID_PASSWORD.getErrorMessage());
                break;

            case INVALID_USERNAME:
                model.addAttribute(BASE_ATTRIBUTE, RegistrationError.INVALID_USERNAME.getErrorMessage());
                break;
        }

        return "registration";
    }
}
