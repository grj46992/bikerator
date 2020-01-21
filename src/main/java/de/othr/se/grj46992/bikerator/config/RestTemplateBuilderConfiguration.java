package de.othr.se.grj46992.bikerator.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class RestTemplateBuilderConfiguration {
    @Bean
    @RequestScope
    public RestTemplate createRestTemplateBuilder(RestTemplateBuilder builder) {
        return builder.build();
    }
}
