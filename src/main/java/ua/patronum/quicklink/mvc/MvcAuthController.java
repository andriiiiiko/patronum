package ua.patronum.quicklink.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.patronum.quicklink.restapi.auth.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MvcAuthController {

    private final AuthService service;

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegistrationRequest registrationRequest, Model model) {
        RegistrationResponse response = service.register(registrationRequest);

        if (response.getError() == RegistrationResponse.Error.OK) {
            return "redirect:/auth/login";
        } else {
            model.addAttribute("error", response.getError().toString());
            return "registration";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, Model model) {
        LoginResponse response = service.login(loginRequest);

        if (response.getError() == LoginResponse.Error.OK) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", response.getError().toString());
            return "redirect:/auth/login";
        }
    }
}