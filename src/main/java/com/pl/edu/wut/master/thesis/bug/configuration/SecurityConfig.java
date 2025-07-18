//package com.pl.edu.wut.master.thesis.bug.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                // we’re doing a pure JSON API, so turn off CSRF protection
//                .csrf(csrf -> csrf.disable())
//
//                // allow register without any credentials,
//                // but require auth for everything else
//                .authorizeHttpRequests(authz -> authz
//                        .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                // pick your favorite: formLogin(), httpBasic(), JWT, etc.
//                .httpBasic(Customizer.withDefaults());
//
//        return http.build();
//    }
//}
//
