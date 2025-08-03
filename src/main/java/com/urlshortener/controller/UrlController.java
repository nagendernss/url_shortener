// src/main/java/com/urlshortener/controller/UrlController.java
package com.urlshortener.controller;

import com.urlshortener.dto.UrlCreateRequest;
import com.urlshortener.dto.UrlResponse;
import com.urlshortener.model.Url;
import com.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("urlCreateRequest", new UrlCreateRequest());
        return "index";
    }

    @PostMapping("/api/shorten")
    @ResponseBody
    public ResponseEntity<?> shortenUrl(@Valid @RequestBody UrlCreateRequest request,
                                        HttpServletRequest httpRequest) {
        try {
            Url url = urlService.createShortUrl(request);
            String baseUrl = getBaseUrl(httpRequest);
            UrlResponse response = new UrlResponse(url, baseUrl);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @PostMapping("/shorten")
    public String shortenUrlForm(@Valid @ModelAttribute UrlCreateRequest request,
                                 BindingResult bindingResult,
                                 Model model,
                                 HttpServletRequest httpRequest) {
        if (bindingResult.hasErrors()) {
            return "index";
        }

        try {
            Url url = urlService.createShortUrl(request);
            String baseUrl = getBaseUrl(httpRequest);
            UrlResponse response = new UrlResponse(url, baseUrl);
            model.addAttribute("result", response);
            model.addAttribute("urlCreateRequest", new UrlCreateRequest());
            return "index";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/{shortId}")
    public ResponseEntity<?> redirect(@PathVariable String shortId, HttpServletRequest request) {
        String ipAddress = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String referer = request.getHeader("Referer");

        String originalUrl = urlService.redirect(shortId, ipAddress, userAgent, referer);

        if (originalUrl != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", originalUrl)
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/api/url/{shortId}")
    @ResponseBody
    public ResponseEntity<?> getUrl(@PathVariable String shortId, HttpServletRequest httpRequest) {
        return urlService.getUrlWithAnalytics(shortId)
                .map(url -> {
                    String baseUrl = getBaseUrl(httpRequest);
                    return ResponseEntity.ok(new UrlResponse(url, baseUrl));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    private String getBaseUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() +
                (request.getServerPort() != 80 && request.getServerPort() != 443 ?
                        ":" + request.getServerPort() : "");
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return xForwardedForHeader.split(",")[0];
        }
    }
}
