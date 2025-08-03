// src/main/java/com/urlshortener/repository/ClickEventRepository.java
package com.urlshortener.repository;

import com.urlshortener.model.ClickEvent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends MongoRepository<ClickEvent, String> {
    List<ClickEvent> findByShortIdOrderByClickedAtDesc(String shortId, Pageable pageable);
    List<ClickEvent> findByShortIdAndClickedAtBetween(String shortId, LocalDateTime start, LocalDateTime end);
    long countByShortId(String shortId);
}
