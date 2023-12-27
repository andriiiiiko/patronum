package ua.patronum.quicklink.mvc.controller;

import ua.patronum.quicklink.mvc.service.UserService;

public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


}
