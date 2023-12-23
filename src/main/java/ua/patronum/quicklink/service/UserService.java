package ua.patronum.quicklink.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.patronum.quicklink.model.User;
import ua.patronum.quicklink.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    public User findByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);

        if (user.isEmpty()) {
            return null;
        }
        return user.get();
    }

    public void saveUser(User user) {
        repository.save(user);
    }
}