package com.dualforce.fitquest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger 허용
                        .anyRequest().authenticated() // 모든 요청 인증 요구
                )
                .formLogin(form -> form.disable()) // Form Login 비활성화
                .httpBasic(basic -> basic.disable()); // HTTP Basic 비활성화

        return http.build();
    }
}
