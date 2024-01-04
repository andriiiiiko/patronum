package ua.patronum.quicklink.mvc.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.patronum.quicklink.restapi.auth.RegistrationRequest;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mvc/auth")
public class MvcAuthController {

    private final MvcAuthService service;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest registrationRequest, Model model) {
        return service.registration(model, registrationRequest);
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
