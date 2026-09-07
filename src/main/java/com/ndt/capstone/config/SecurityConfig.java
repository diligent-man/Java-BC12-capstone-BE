package com.ndt.capstone.config;

import com.ndt.capstone.filter.AuthFilter;


import org.springframework.http.HttpMethod;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configSecurityFilterChain(HttpSecurity http, AuthFilter authenFilter) {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(authenFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(
                authorizer -> {
                    authorizer.requestMatchers("/api/jwt/*").permitAll();
                    authorizer.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    authorizer.requestMatchers(HttpMethod.GET, "/product/**").permitAll();
                    authorizer.requestMatchers(HttpMethod.GET, "/brand/**").permitAll();
                    authorizer.requestMatchers("/file/**").permitAll(); // Cho phép truy cập các API liên quan đến file
                    authorizer.requestMatchers("/error").permitAll(); // Cho phép Spring Boot hiển thị đúng mã lỗi thực sự (VD: 400, 500) thay vì bị chặn thành 403
                    authorizer.requestMatchers(HttpMethod.POST, "/api/admin/**").hasAuthority("ROLE_ADMIN");

                    // tất cả các request còn lại đều phải chứng thực
                    authorizer.anyRequest().authenticated();
                }
            )
            .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:8080",
                "http://localhost:5500",
                "http://localhost:3979",
                "http://localhost:5173"
            )
        );

        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);  // không sử dụng cookie thì set false

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
