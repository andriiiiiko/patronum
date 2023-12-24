package ua.patronum.quicklink.auth.dto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.auth.dto.model.User;
import ua.patronum.quicklink.auth.dto.repository.UserRepository;

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
