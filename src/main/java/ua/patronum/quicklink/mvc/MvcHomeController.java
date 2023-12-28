package ua.patronum.quicklink.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class MvcHomeController {
    // private final UrlService service;

    @GetMapping()
    public String showHomePage(Model model) {
        //List<URL> urlList service.getAllUrls();
        //  model.addAttribute("urlList", urlList);

        // if ( authorize)
        return "home-authorized";
        //else (not authorize)
        //return "home-not-authorized";
    }

    @GetMapping("/active")
    public String showAllActiveUrl(Model model) {
        //List<Url> urlList = service.getAllActiveUrls();
        //  model.addAttribute("urlList", urlList);
        return "home-authorized";
    }

    @GetMapping("/user/list")
    public String showAllUserURL(Model model) {
        //List<Url> urlList = service.getAllUserUrls();
        //  model.addAttribute("urlList", urlList);
        return "home-authorized";
    }

    @GetMapping("/user/list/active")
    public String showAllUserActiveURL(Model model) {
        //List<Url> urlList = service.getAllUserActiveUrls();
        //  model.addAttribute("urlList", urlList);
        return "home-authorized";
    }

    @PostMapping("/creat")
    public String create(String url, Model model) {
        //service.create(url)
        return "redirect:/home";
    }
}


