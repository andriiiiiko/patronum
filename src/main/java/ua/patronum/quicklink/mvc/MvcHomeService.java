package ua.patronum.quicklink.mvc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.restapi.url.Error;
import ua.patronum.quicklink.restapi.url.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MvcHomeService {

    private static final String USER_LIST_FLAG = "isUserListPage";
    private static final String BASE_TEMPLATE = "home-authorized";
    private static final String BASE_ATTRIBUTE = "urlList";
    private final UrlServiceImpl service;
    private final UrlRepository repository;

    public String showHomePage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UrlDto> urlList = service.getAllUrls().getUrls();
        model.addAttribute(BASE_ATTRIBUTE, urlList);

        if (!Objects.equals(username, "anonymousUser")) {
            return BASE_TEMPLATE;
        } else {
            return "home-not-authorized";
        }
    }

    public String showAllActiveUrl(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UrlDto> activeUrls = service.getAllActiveUrls().getActiveUrls();
        model.addAttribute(BASE_ATTRIBUTE, activeUrls);

        if (!Objects.equals(username, "anonymousUser")) {
            return BASE_TEMPLATE;
        } else {
            return "home-not-authorized";
        }
    }

    public String showAllUserURL(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UrlDto> userUrls = service.getAllUserUrls(username).getUserUrls();
        model.addAttribute(BASE_ATTRIBUTE, userUrls);
        model.addAttribute(USER_LIST_FLAG, true);

        return BASE_TEMPLATE;
    }

    public String showAllUserActiveURL(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UrlDto> activeUserUrls = service.getAllUserActiveUrl(username).getActiveUserUrls();
        model.addAttribute(BASE_ATTRIBUTE, activeUserUrls);
        model.addAttribute(USER_LIST_FLAG, true);

        return BASE_TEMPLATE;
    }

    public String create(CreateUrlRequest request, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CreateUrlResponse response = service.createUrl(username, request);

        if (!response.getError().equals(Error.OK)) {
            List<UrlDto> urls = service.getAllUrls().getUrls();
            model.addAttribute("error", "Invalid URL");
            model.addAttribute(BASE_ATTRIBUTE, urls);
            return BASE_TEMPLATE;
        }

        return "redirect:/home";
    }

    public String delete(Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        service.deleteUrlById(username, id);

        return "redirect:/home/user/list";
    }

    public String redirect(Long id) {
        Optional<Url> byId = repository.findById(id);

        if (byId.isEmpty()) {
            return "redirect:/home";
        }

        Url url = byId.get();
        RedirectRequest request = new RedirectRequest();
        request.setShortUrl(url.getShortUrl());
        RedirectResponse response = service.redirectOriginalUrl(request);
        String originalUrl = response.getOriginalUrl();

        return "redirect:" + originalUrl;
    }
}
