package com.emb.event_management_backend.config;

import com.emb.event_management_backend.filter.JWTFilter;
import com.emb.event_management_backend.services.CustomUserDetailsService;
import com.emb.event_management_backend.services.UserService;
import com.emb.event_management_backend.utils.JWTUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(JWTUtil jwtUtil, UserService userService, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public JWTFilter jwtFilter() {
        return new JWTFilter(jwtUtil, userService);  // Create the JWTFilter bean with dependencies
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/users/register", "/api/users/login"))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/users/register", "/api/users/login").permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic().disable();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }
}
