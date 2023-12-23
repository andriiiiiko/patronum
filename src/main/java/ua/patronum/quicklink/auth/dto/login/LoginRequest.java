package ua.patronum.quicklink.auth.dto.login;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}