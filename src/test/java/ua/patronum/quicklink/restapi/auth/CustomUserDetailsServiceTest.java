package ua.patronum.quicklink.restapi.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.patronum.quicklink.data.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        ua.patronum.quicklink.data.entity.User user = new ua.patronum.quicklink.data.entity.User();
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = service.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());

        // Verify that userRepository.findByUsername was called with the correct username
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void loadUserByUsername_UserNotExists_ThrowsUsernameNotFoundException() {
        // Arrange
        String username = "nonexistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(username));

        // Verify that userRepository.findByUsername was called with the correct username
        verify(userRepository, times(1)).findByUsername(username);
    }
}