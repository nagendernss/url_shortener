// src/main/java/com/urlshortener/service/AnalyticsService.java
package com.urlshortener.service;

import com.urlshortener.model.AnalyticsResponse;
import com.urlshortener.model.ClickEvent;
import com.urlshortener.model.Url;
import com.urlshortener.repository.ClickEventRepository;
import com.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    @Autowired
    private UrlRepository urlRepository;

    @Autowired
    private ClickEventRepository clickEventRepository;

    public Optional<AnalyticsResponse> getAnalytics(String shortId) {
        Optional<Url> urlOpt = urlRepository.findByShortId(shortId);

        if (urlOpt.isEmpty()) {
            return Optional.empty();
        }

        Url url = urlOpt.get();

        // Get recent clicks (last 10)
        List<ClickEvent> recentClicks = clickEventRepository
                .findByShortIdOrderByClickedAtDesc(shortId, PageRequest.of(0, 10));

        // Get all clicks for analytics
        List<ClickEvent> allClicks = clickEventRepository
                .findByShortIdAndClickedAtBetween(shortId,
                        LocalDateTime.now().minusDays(30),
                        LocalDateTime.now());

        // Group clicks by date
        Map<String, Long> clicksByDate = allClicks.stream()
                .collect(Collectors.groupingBy(
                        click -> click.getClickedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Collectors.counting()
                ));

        // Group clicks by country
        Map<String, Long> clicksByCountry = allClicks.stream()
                .filter(click -> click.getCountry() != null)
                .collect(Collectors.groupingBy(
                        ClickEvent::getCountry,
                        Collectors.counting()
                ));

        // Group clicks by referer
        Map<String, Long> clicksByReferer = allClicks.stream()
                .filter(click -> click.getReferer() != null && !click.getReferer().isEmpty())
                .collect(Collectors.groupingBy(
                        click -> {
                            String referer = click.getReferer();
                            if (referer.contains("google")) return "Google";
                            if (referer.contains("facebook")) return "Facebook";
                            if (referer.contains("twitter")) return "Twitter";
                            if (referer.contains("linkedin")) return "LinkedIn";
                            return "Direct";
                        },
                        Collectors.counting()
                ));

        AnalyticsResponse response = new AnalyticsResponse();
        response.setShortId(shortId);
        response.setOriginalUrl(url.getOriginalUrl());
        response.setTotalClicks(url.getClickCount());
        response.setCreatedAt(url.getCreatedAt());
        response.setRecentClicks(recentClicks);
        response.setClicksByDate(clicksByDate);
        response.setClicksByCountry(clicksByCountry);
        response.setClicksByReferer(clicksByReferer);

        return Optional.of(response);
    }
}
