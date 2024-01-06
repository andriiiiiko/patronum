package ua.patronum.quicklink.restapi.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtUtil jwtUtil;

    @Test
    void generateToken() {
        String username = "Test";
        UserDetails userDetails = new User(username, "password", Collections.emptyList());
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);
        String token = jwtUtil.generateToken((username));
        assertNotNull(token);
    }

    @Test
    void validateToken_ValidAndInvalidTokens_ReturnsCorrectResults() {
        String validUsername = "ValidUser";
        UserDetails validUserDetails = new User(validUsername, "password", Collections.emptyList());

        String invalidUsername = "InvalidUser";
        UserDetails invalidUserDetails = new User(invalidUsername, "password", Collections.emptyList());

        when(userDetailsService.loadUserByUsername(validUsername)).thenReturn(validUserDetails);
        when(userDetailsService.loadUserByUsername(invalidUsername)).thenReturn(invalidUserDetails);

        String validTokenForValidUser = jwtUtil.generateToken(validUsername);
        String invalidTokenForInvalidUser = jwtUtil.generateToken(invalidUsername);

        assertTrue(jwtUtil.validateToken(validTokenForValidUser, validUserDetails));

        assertFalse(jwtUtil.validateToken(invalidTokenForInvalidUser, validUserDetails));
    }
}