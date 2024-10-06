package com.example.loginback.config;

import com.example.loginback.security.CustomAuthorityMapper;
import com.example.loginback.security.CustomOAuth2UserService;
import com.example.loginback.security.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
// We should Add this method security annotation to use 'PreAuthorize' annotation in controller
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**", "/js/**", "/images/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated());
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                        .userService(customOAuth2UserService)
                        .oidcUserService(customOidcUserService)));
        http.logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll());
        return http.build();
    }

    @Bean
    public GrantedAuthoritiesMapper customAuthoritiesMapper() {
        return new CustomAuthorityMapper();
    }

}
