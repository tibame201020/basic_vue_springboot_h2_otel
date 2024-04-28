package com.tibame201020.backend.config.security;

import com.tibame201020.backend.constant.Role;
import com.tibame201020.backend.filter.PreSecurityCheckJsonWebToken;
import com.tibame201020.backend.handler.SecurityEventHandler;
import com.tibame201020.backend.service.AdminUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

/**
 * 後台security設定
 * for config
 * /api/backoffice/login => adminUserDetailService
 */
@Configuration
@RequiredArgsConstructor
@Order(2)
public class BackOfficeSecurityConfig {
    private final SecurityEventHandler securityEventHandler;
    private final PreSecurityCheckJsonWebToken preSecurityCheckJsonWebToken;
    private final AdminUserDetailService adminUserDetailService;

    @Bean
    public SecurityFilterChain filterChainApp1(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
         http.securityMatcher("/api/backoffice/**")
                .formLogin(httpSecurityFormLoginConfigurer ->
                        httpSecurityFormLoginConfigurer.loginPage("/api/backoffice/login")
                                .usernameParameter("account")
                                .passwordParameter("password")
                                .successHandler(securityEventHandler)
                                .failureHandler(securityEventHandler)
                );

        http.authorizeHttpRequests(
                req -> {
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/backoffice/**"))
                            .hasRole(Role.ADMIN.name());
                    req.anyRequest().authenticated();
                }
        );

        http.userDetailsService(adminUserDetailService);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(preSecurityCheckJsonWebToken, UsernamePasswordAuthenticationFilter.class);
        http.cors(corsConfiguration -> corsConfiguration.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()));
        http.exceptionHandling(exceptionConfiguration -> exceptionConfiguration.authenticationEntryPoint(securityEventHandler));
        http.exceptionHandling(exceptionConfiguration -> exceptionConfiguration.accessDeniedHandler(securityEventHandler));

         return http.build();
    }
}
