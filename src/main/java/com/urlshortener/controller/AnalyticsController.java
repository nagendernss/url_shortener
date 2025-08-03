// src/main/java/com/urlshortener/controller/AnalyticsController.java
package com.urlshortener.controller;

import com.urlshortener.model.AnalyticsResponse;
import com.urlshortener.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/analytics/{shortId}")
    public String analyticsPage(@PathVariable String shortId, Model model) {
        return analyticsService.getAnalytics(shortId)
                .map(analytics -> {
                    model.addAttribute("analytics", analytics);
                    return "analytics";
                })
                .orElse("redirect:/");
    }

    @GetMapping("/api/analytics/{shortId}")
    @ResponseBody
    public ResponseEntity<AnalyticsResponse> getAnalytics(@PathVariable String shortId) {
        return analyticsService.getAnalytics(shortId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
