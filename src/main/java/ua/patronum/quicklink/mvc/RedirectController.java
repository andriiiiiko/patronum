package ua.patronum.quicklink.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    @GetMapping
    public String redirectToHomePage(){
        return "redirect:/mvc/home";
    }
}
