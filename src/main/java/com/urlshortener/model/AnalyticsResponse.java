// src/main/java/com/urlshortener/model/AnalyticsResponse.java
package com.urlshortener.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AnalyticsResponse {
    private String shortId;
    private String originalUrl;
    private long totalClicks;
    private LocalDateTime createdAt;
    private List<ClickEvent> recentClicks;
    private Map<String, Long> clicksByDate;
    private Map<String, Long> clicksByCountry;
    private Map<String, Long> clicksByReferer;

    // Constructors
    public AnalyticsResponse() {}

    // Getters and Setters
    public String getShortId() { return shortId; }
    public void setShortId(String shortId) { this.shortId = shortId; }

    public String getOriginalUrl() { return originalUrl; }
    public void setOriginalUrl(String originalUrl) { this.originalUrl = originalUrl; }

    public long getTotalClicks() { return totalClicks; }
    public void setTotalClicks(long totalClicks) { this.totalClicks = totalClicks; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<ClickEvent> getRecentClicks() { return recentClicks; }
    public void setRecentClicks(List<ClickEvent> recentClicks) { this.recentClicks = recentClicks; }

    public Map<String, Long> getClicksByDate() { return clicksByDate; }
    public void setClicksByDate(Map<String, Long> clicksByDate) { this.clicksByDate = clicksByDate; }

    public Map<String, Long> getClicksByCountry() { return clicksByCountry; }
    public void setClicksByCountry(Map<String, Long> clicksByCountry) { this.clicksByCountry = clicksByCountry; }

    public Map<String, Long> getClicksByReferer() { return clicksByReferer; }
    public void setClicksByReferer(Map<String, Long> clicksByReferer) { this.clicksByReferer = clicksByReferer; }
}
