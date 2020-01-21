package de.othr.se.grj46992.bikerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.SecureRandom;

@Configuration
public class SecurityUtilities {

    private static String salt = "Super-streng-geh€1m";

    @Bean
    @Scope("singleton")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15, new SecureRandom(salt.getBytes()));
    }
}
