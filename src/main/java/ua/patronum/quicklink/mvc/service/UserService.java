package ua.patronum.quicklink.mvc.service;

import ua.patronum.quicklink.data.entity.User;
import ua.patronum.quicklink.mvc.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}

/*
методы для юзера
регистрация
логин
лог аут

 */
