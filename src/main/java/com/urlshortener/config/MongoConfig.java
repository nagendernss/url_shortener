// src/main/java/com/urlshortener/config/MongoConfig.java
package com.urlshortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "url_shortener";
    }
}
