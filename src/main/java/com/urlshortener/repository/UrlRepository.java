// src/main/java/com/urlshortener/repository/UrlRepository.java
package com.urlshortener.repository;

import com.urlshortener.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
    Optional<Url> findByShortId(String shortId);
    Optional<Url> findByShortIdAndActiveTrue(String shortId);
    boolean existsByShortId(String shortId);
}
