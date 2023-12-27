package ua.patronum.quicklink.mvc.controller;

import ua.patronum.quicklink.mvc.service.UserService;

public class UrlController {
    private final UserService userService;

    public UrlController(UserService userService) {
        this.userService = userService;
    }

}
