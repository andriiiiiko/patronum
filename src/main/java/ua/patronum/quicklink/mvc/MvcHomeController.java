package ua.patronum.quicklink.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class MvcHomeController {
    private static final String USER_LIST_FLAG = "isUserListPage";
    private static final String BASE_TEMPLATE = "home-authorized";

    @GetMapping()
    public String showHomePage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!Objects.equals(username, "anonymousUser")) {
            return BASE_TEMPLATE;
        } else {
            return "home-not-authorized";
        }
    }

    @GetMapping("/active")
    public String showAllActiveUrl(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!Objects.equals(username, "anonymousUser")) {
            return BASE_TEMPLATE;
        } else {
            return "home-not-authorized";
        }
    }

    @GetMapping("/user/list")
    public String showAllUserURL(Model model) {
        model.addAttribute(USER_LIST_FLAG, true);
        return BASE_TEMPLATE;
    }

    @GetMapping("/user/list/active")
    public String showAllUserActiveURL(Model model) {
        model.addAttribute(USER_LIST_FLAG, true);
        return BASE_TEMPLATE;
    }

    @PostMapping("/creat")
    public String create(String url, Model model) {
        return "redirect:/home";
    }

    @PostMapping("/user/list/delete/{id}")
    public String delete(Model model, @PathVariable Long id) {
        return "redirect:/user/list";
    }
}