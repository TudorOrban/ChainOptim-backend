package org.chainoptim.config.security;

import org.chainoptim.core.user.service.UserDetailsServiceImpl;
import org.chainoptim.core.user.jwt.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/v1/login", "/api/v1/validate-token", "/api/v1/get-username-from-token").permitAll()
                        .requestMatchers("/api/v1/users/**").permitAll()
                        .requestMatchers("/api/v1/user-settings/**").permitAll()
                        .requestMatchers("/api/v1/organization/**").permitAll()
                        .requestMatchers("/api/v1/organization-invites/**").permitAll()
                        .requestMatchers("/api/v1/organization-requests/**").permitAll()
                        .requestMatchers("/api/v1/supply-chain-snapshots/**").permitAll()
                        .requestMatchers("/api/v1/custom-roles/**").permitAll()
                        .requestMatchers("/api/v1/products/**").permitAll()
                        .requestMatchers("/api/v1/stages/**").permitAll()
                        .requestMatchers("/api/v1/units-of-measurement/**").permitAll()
                        .requestMatchers("/api/v1/raw-materials/**").permitAll()
                        .requestMatchers("/api/v1/components/**").permitAll()
                        .requestMatchers("/api/v1/factories/**").permitAll()
                        .requestMatchers("/api/v1/factory-stages/**").permitAll()
                        .requestMatchers("/api/v1/warehouses/**").permitAll()
                        .requestMatchers("/api/v1/suppliers/**").permitAll()
                        .requestMatchers("/api/v1/supplier-orders/**").permitAll()
                        .requestMatchers("/api/v1/suppliers/shipments/**").permitAll()
                        .requestMatchers("/api/v1/clients/**").permitAll()
                        .requestMatchers("/api/v1/client-orders/**").permitAll()
                        .requestMatchers("/api/v1/clients/shipments/**").permitAll()
                        .requestMatchers("/api/v1/graphs/**").permitAll()
                        .requestMatchers("/api/v1/product-graphs/**").permitAll()
                        .requestMatchers("/api/v1/locations/**").permitAll()
                        .requestMatchers("/api/v1/payments/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsService)
                .authenticationManager(authenticationManagerBean());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() {
        return new ProviderManager(Collections.singletonList(daoAuthenticationProvider()));
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }
}