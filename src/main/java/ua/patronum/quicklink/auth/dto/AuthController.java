package ua.patronum.quicklink.auth.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.patronum.quicklink.auth.dto.login.LoginRequest;
import ua.patronum.quicklink.auth.dto.login.LoginResponse;
import ua.patronum.quicklink.auth.dto.registration.RegistrationRequest;
import ua.patronum.quicklink.auth.dto.registration.RegistrationResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/project/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public RegistrationResponse register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse register(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}