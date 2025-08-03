// src/main/java/com/urlshortener/model/Url.java
package com.urlshortener.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Document(collection = "urls")
public class Url {
    @Id
    private String id;

    @Indexed(unique = true)
    private String shortId;

    private String originalUrl;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private long clickCount;
    private boolean active;
    private String createdBy;

    // Constructors
    public Url() {
        this.createdAt = LocalDateTime.now();
        this.clickCount = 0;
        this.active = true;
    }

    public Url(String shortId, String originalUrl) {
        this();
        this.shortId = shortId;
        this.originalUrl = originalUrl;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getShortId() { return shortId; }
    public void setShortId(String shortId) { this.shortId = shortId; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getExpiresAt() { return expiresAt; }
    public void setExpiresAt(LocalDateTime expiresAt) { this.expiresAt = expiresAt; }

    public long getClickCount() { return clickCount; }
    public void setClickCount(long clickCount) { this.clickCount = clickCount; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public void incrementClickCount() {
        this.clickCount++;
    }
}
