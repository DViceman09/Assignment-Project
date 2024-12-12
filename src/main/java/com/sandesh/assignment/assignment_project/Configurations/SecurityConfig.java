package com.sandesh.assignment.assignment_project.Configurations;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


import java.util.Arrays;
import java.util.Collections;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement((management) -> {
            management.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }).authorizeHttpRequests((Authorize) -> {
            ((AuthorizedUrl)((AuthorizedUrl)((AuthorizedUrl)Authorize.requestMatchers(new String[]{"/admin/api/**"})).hasAnyRole(new String[]{"ADMIN"}).requestMatchers(new String[]{"/api/**"})).authenticated().anyRequest()).permitAll();
        }).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(AbstractHttpConfigurer::disable).cors((cors) -> {
            cors.configurationSource(this.corsConfigurationSource());
        });
        return (SecurityFilterChain)http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg = new CorsConfiguration();
                cfg.setAllowedOrigins(Arrays.asList(
                        "http://localhost:8080"
                ));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);
                return cfg;
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encode passwords for security
    }
}
