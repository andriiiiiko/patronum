package ua.patronum.quicklink.restapi.url;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/create")
    public CreateUrlResponse createUrl(Principal principal, @RequestBody CreateUrlRequest request) {
        return urlService.createUrl(principal.getName(), request);
    }

    @GetMapping("/view/all")
    public GetAllUrlsResponse getAllUrls() {
        return urlService.getAllUrls();
    }

    @GetMapping("/view/all/user")
    public GetAllUserUrlsResponse getUserUrls(@RequestBody String username) {
        return urlService.getAllUserUrls(username);
    }

    @GetMapping("/view/all/user/active")
    public GetAllUserActiveUrlsResponse getAllUserActiveUrl(Principal principal) {
        return urlService.getAllUserActiveUrl(principal.getName());
    }

    @PostMapping("/delete/{id}")
    public DeleteUrlResponse deleteUrlById(@PathVariable Long id) {
        return urlService.deleteUrlById(id);
    }

    @PostMapping("/view/redirect")
    public RedirectResponse redirectOriginalUrl(@RequestBody RedirectRequest request) {
        return urlService.redirectOriginalUrl(request);
    }

    @GetMapping("/view/all/active")
    public GetAllActiveUrlsResponse getAllActiveUrls() {
        return urlService.getAllActiveUrls();
    }
}
