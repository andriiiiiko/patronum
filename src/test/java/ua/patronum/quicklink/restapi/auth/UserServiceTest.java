package ua.patronum.quicklink.restapi.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.data.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    void testFindByUsername() {

        User user = User.builder().username("test").password("password").build();
        when(repository.findByUsername("test")).thenReturn(java.util.Optional.of(user));

        User foundUser = service.findByUsername("test");
        assertNotNull(foundUser);
        assertEquals("test", foundUser.getUsername());
        assertEquals("password", foundUser.getPassword());

        verify(repository, times(1)).findByUsername("test");
    }

    @Test
    void testSaveUser() {

        User user = User.builder().username("Test").password("Test").build();

        service.saveUser(user);

        verify(repository, times(1)).save(user);
    }
}