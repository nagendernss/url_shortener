// src/main/java/com/urlshortener/dto/UrlResponse.java
package com.urlshortener.dto;

import com.urlshortener.model.Url;

import java.time.LocalDateTime;

public class UrlResponse {
    private String shortId;
    private String shortUrl;
    private String originalUrl;
    private String title;
    private LocalDateTime createdAt;
    private long clickCount;

    // Constructors
    public UrlResponse() {}

    public UrlResponse(Url url, String baseUrl) {
        this.shortId = url.getShortId();
        this.shortUrl = baseUrl + "/" + url.getShortId();
        this.originalUrl = url.getOriginalUrl();
        this.title = url.getTitle();
        this.createdAt = url.getCreatedAt();
        this.clickCount = url.getClickCount();
    }

    // Getters and Setters
    public String getShortId() { return shortId; }
    public void setShortId(String shortId) { this.shortId = shortId; }

    public String getShortUrl() { return shortUrl; }
    public void setShortUrl(String shortUrl) { this.shortUrl = shortUrl; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public long getClickCount() { return clickCount; }
    public void setClickCount(long clickCount) { this.clickCount = clickCount; }
}
