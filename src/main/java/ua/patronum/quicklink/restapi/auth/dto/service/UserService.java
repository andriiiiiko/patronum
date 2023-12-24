package ua.patronum.quicklink.restapi.auth.dto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.data.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User findByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        return user.orElse(null);
    }

    public void saveUser(User user) {
        repository.save(user);
    }
}
