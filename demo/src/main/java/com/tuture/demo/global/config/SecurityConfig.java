package com.tuture.demo.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuture.demo.global.security.CustomAccessDeniedHandler;
import com.tuture.demo.global.security.CustomAuthenticationEntryPoint;
import com.tuture.demo.global.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // cors 에러
        http.cors(AbstractHttpConfigurer::disable);

        // 모든 경로에 대한 권한 해제코드 : 테스트시 사용!!
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/user/*").permitAll()
                        .anyRequest().permitAll());
        // 모든페이지 접근 허용
        // exceptionHandler
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper))
                .accessDeniedHandler(new CustomAccessDeniedHandler())
        );

        // jwt filter
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
