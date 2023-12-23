package ua.patronum.quicklink.auth.dto.registration;

import lombok.Data;

@Data
public class RegistrationRequest {

    private String username;
    private String password;
    private String confirmPassword;
}
