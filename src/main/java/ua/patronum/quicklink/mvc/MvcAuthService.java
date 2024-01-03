package ua.patronum.quicklink.mvc;

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

        if (response.getError() == RegistrationResponse.Error.OK) {
            return "redirect:/auth/login";

        } else if (response.getError() == RegistrationResponse.Error.USER_ALREADY_EXISTS) {
            model.addAttribute(BASE_ATTRIBUTE, "User already exists");

        } else if (response.getError() == RegistrationResponse.Error.INVALID_CONFIRM_PASSWORD) {
            model.addAttribute(BASE_ATTRIBUTE, "Invalid confirm password");

        } else if (response.getError() == RegistrationResponse.Error.INVALID_PASSWORD) {
            model.addAttribute(BASE_ATTRIBUTE, "Invalid password");

        } else if (response.getError() == RegistrationResponse.Error.INVALID_USERNAME)
            model.addAttribute(BASE_ATTRIBUTE, "Invalid username");

        return "registration";
    }
}
