package ua.patronum.quicklink.restapi.url;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
@Tag(name = "URL Controller",
        description = "Endpoints for URL")
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/create")
    @Operation(summary = "Create a new ShortURL",
            description = "Endpoint to create a new ShortURL.")
    public CreateUrlResponse createUrl(Principal principal, @RequestBody CreateUrlRequest request) {
        return urlService.createUrl(principal.getName(), request);
    }

    @GetMapping("/view/all")
    @Operation(summary = "View all ShortURL",
            description = "Endpoint to view all ShortURL.")
    public GetAllUrlsResponse getAllUrls() {
        return urlService.getAllUrls();
    }

    @GetMapping("/view/all/user")
    @Operation(summary = "View all user's ShortURL",
            description = "Endpoint to view all user's ShortURL")
    public GetAllUserUrlsResponse getUserUrls(Principal principal) {
        return urlService.getAllUserUrls(principal.getName());
    }

    @GetMapping("/view/all/user/active")
    @Operation(summary = "View all active user's ShortURL",
            description = "Endpoint to view all  active user's ShortURL")
    public GetAllUserActiveUrlsResponse getAllUserActiveUrl(Principal principal) {
        return urlService.getAllUserActiveUrl(principal.getName());
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "Delete ShortURL by ID",
            description = "Endpoint to delete ShortURL by ID")
    public DeleteUrlResponse deleteUrlById(Principal principal, @PathVariable Long id) {
        return urlService.deleteUrlById(principal.getName(), id);
    }

    @PostMapping("/view/redirect")
    @Operation(summary = "Redirect",
            description = "Endpoint to redirect")
    public RedirectResponse redirectOriginalUrl(@RequestBody RedirectRequest request) {
        return urlService.redirectOriginalUrl(request);
    }

    @GetMapping("/view/all/active")
    @Operation(summary = "View all active ShortURL",
            description = "Endpoint to view all active ShortURL")
    public GetAllActiveUrlsResponse getAllActiveUrls() {
        return urlService.getAllActiveUrls();
    }
}
