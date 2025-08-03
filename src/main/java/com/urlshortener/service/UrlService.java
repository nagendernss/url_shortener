// src/main/java/com/urlshortener/service/UrlService.java
package com.urlshortener.service;

import com.urlshortener.dto.UrlCreateRequest;
import com.urlshortener.model.ClickEvent;
import com.urlshortener.model.Url;
import com.urlshortener.repository.ClickEventRepository;
import com.urlshortener.repository.UrlRepository;
import com.urlshortener.util.ShortIdGenerator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ClickEventRepository clickEventRepository;

    private final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    public Url createShortUrl(UrlCreateRequest request) {
        // Validate URL
        if (!urlValidator.isValid(request.getUrl())) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        // Generate or validate custom alias
        String shortId;
        if (request.getCustomAlias() != null && !request.getCustomAlias().trim().isEmpty()) {
            shortId = request.getCustomAlias().trim();

            if (!ShortIdGenerator.isValidCustomAlias(shortId)) {
                throw new IllegalArgumentException("Custom alias must be 3-20 characters long and contain only letters, numbers, and hyphens");
            }

            if (urlRepository.existsByShortId(shortId)) {
                throw new IllegalArgumentException("Custom alias already exists");
            }
        } else {
            do {
                shortId = ShortIdGenerator.generate();
            } while (urlRepository.existsByShortId(shortId));
        }

        // Create URL entity
        Url url = new Url(shortId, request.getUrl());
        url.setTitle(request.getTitle());

        // Set expiration if specified
        if (request.getExpiresInDays() != null && request.getExpiresInDays() > 0) {
            url.setExpiresAt(LocalDateTime.now().plusDays(request.getExpiresInDays()));
        }

        return urlRepository.save(url);
    }

    public Optional<Url> getUrlByShortId(String shortId) {
        return urlRepository.findByShortIdAndActiveTrue(shortId);
    }

    public String redirect(String shortId, String ipAddress, String userAgent, String referer) {
        Optional<Url> urlOpt = getUrlByShortId(shortId);

        if (urlOpt.isEmpty()) {
            return null;
        }

        Url url = urlOpt.get();

        // Check if URL has expired
        if (url.getExpiresAt() != null && LocalDateTime.now().isAfter(url.getExpiresAt())) {
            return null;
        }

        // Record click event
        ClickEvent clickEvent = new ClickEvent(shortId, ipAddress, userAgent, referer);
        clickEventRepository.save(clickEvent);

        // Increment click count
        url.incrementClickCount();
        urlRepository.save(url);

        return url.getOriginalUrl();
    }

    public Optional<Url> getUrlWithAnalytics(String shortId) {
        return urlRepository.findByShortId(shortId);
    }
}
