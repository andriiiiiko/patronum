package ua.patronum.quicklink.restapi.auth.dto.login;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}