package ua.patronum.quicklink.mvc.home;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ua.patronum.quicklink.data.entity.Url;
import ua.patronum.quicklink.data.repository.UrlRepository;
import ua.patronum.quicklink.mvc.MvcError;
import ua.patronum.quicklink.restapi.url.Error;
import ua.patronum.quicklink.restapi.url.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MvcHomeService {

    private static final String USER_LIST_FLAG = "isUserListPage";
    private static final String AUTHORIZED_TEMPLATE = "home-authorized";
    private static final String NOT_AUTHORIZED_TEMPLATE = "home-not-authorized";
    private static final String BASE_ATTRIBUTE = "urlList";
    private static final String BASE_USERNAME = "anonymousUser";
    private final UrlServiceImpl service;
    private final UrlRepository repository;

    public String showHomePage(Model model) {
        String username = getUsername();
        List<UrlDto> urlList = service.getAllUrls().getUrls();
        model.addAttribute(BASE_ATTRIBUTE, urlList);

        return templateValidator(username);
    }

    public String showAllActiveUrl(Model model) {
        String username = getUsername();
        List<UrlDto> activeUrls = service.getAllActiveUrls().getUrls();
        model.addAttribute(BASE_ATTRIBUTE, activeUrls);

        return templateValidator(username);
    }

    public String showAllUserURL(Model model) {
        String username = getUsername();
        List<UrlDto> userUrls = service.getAllUserUrls(username).getUserUrls();
        model.addAttribute(BASE_ATTRIBUTE, userUrls);
        model.addAttribute(USER_LIST_FLAG, true);

        return AUTHORIZED_TEMPLATE;
    }

    public String showAllUserActiveURL(Model model) {
        String username = getUsername();
        List<UrlDto> activeUserUrls = service.getAllUserActiveUrl(username).getUserUrls();
        model.addAttribute(BASE_ATTRIBUTE, activeUserUrls);
        model.addAttribute(USER_LIST_FLAG, true);

        return AUTHORIZED_TEMPLATE;
    }

    public String create(CreateUrlRequest request, Model model) {
        String username = getUsername();
        CreateUrlResponse response = service.createUrl(username, request);

        if (!response.getError().equals(Error.OK)) {
            List<UrlDto> urls = service.getAllUrls().getUrls();
            model.addAttribute("error", MvcError.INVALID_URL.getErrorMessage());
            model.addAttribute(BASE_ATTRIBUTE, urls);
            return AUTHORIZED_TEMPLATE;
        }

        return "redirect:/mvc/home";
    }

    public String delete(Long id) {
        String username = getUsername();
        service.deleteUrlById(username, id);

        return "redirect:/mvc/home/user/list";
    }

    public String redirect(Long id) {
        Optional<Url> byId = repository.findById(id);

        if (byId.isEmpty()) {
            return "redirect:/mvc/home";
        }

        Url url = byId.get();
        RedirectRequest request = new RedirectRequest();
        request.setShortUrl(url.getShortUrl());
        RedirectResponse response = service.redirectOriginalUrl(request);
        String originalUrl = response.getOriginalUrl();

        return "redirect:" + originalUrl;
    }


     public String getRedirect(Model model){
        List<UrlDto> urls = service.getAllUrls().getUrls();
        String username = getUsername();
        model.addAttribute("error",MvcError.EXPIRED_URL.getErrorMessage());
        model.addAttribute(BASE_ATTRIBUTE,urls);

         return templateValidator(username);
        }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String templateValidator(String username){
    if (!Objects.equals(username, BASE_USERNAME)) {
        return AUTHORIZED_TEMPLATE;
    } else {
        return NOT_AUTHORIZED_TEMPLATE;
        }
    }
}
