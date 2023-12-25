package ua.patronum.quicklink.restapi.url;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/create")
    public CreateUrlResponse createUrl(@RequestParam CreateUrlRequest request) {
        return urlService.createUrl(request);
    }

    @GetMapping("/view/all")
    public GetAllUrlsResponse getAllUrls() {
        return urlService.getAllUrls();
    }

    @GetMapping("/view/all/user")
    public GetAllUserUrlResponse getUserUrls(@RequestParam String username) {
        return urlService.getAllUserUrls(username);
    }

    @GetMapping("/view/all/active")
    public GetAllActiveUrlResponse getAllActiveUrl() {
        return urlService.getAllActiveUrlResponse();
    }

    @PostMapping("/delete/{id}")
    public DeleteUrlResponse deleteUrlById(@PathVariable Long id) {
        return urlService.deleteUrlById(id);
    }
}