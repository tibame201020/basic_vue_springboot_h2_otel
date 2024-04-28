package com.tibame201020.backend.config.security;

import com.tibame201020.backend.constant.Role;
import com.tibame201020.backend.filter.PreSecurityCheckJsonWebToken;
import com.tibame201020.backend.handler.SecurityEventHandler;
import com.tibame201020.backend.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


/**
 * 前台security設定
 * for config
 * /api/backoffice/login => adminUserDetailService
 */
@Configuration
@RequiredArgsConstructor
@Order(2)
public class FrontOfficeSecurityConfig {
    private final SecurityEventHandler securityEventHandler;
    private final PreSecurityCheckJsonWebToken preSecurityCheckJsonWebToken;
    private final UserDetailService userDetailService;

    @Bean(name = "frontOfficeSecurityChain")
    public SecurityFilterChain frontOfficeSecurityChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.securityMatcher(new MvcRequestMatcher(introspector, "/api/frontoffice/**"))
        .formLogin(loginConfiguration -> {
            loginConfiguration.loginPage("/api/frontoffice/login")
                    .usernameParameter("account")
                    .passwordParameter("password");

            loginConfiguration.successHandler(securityEventHandler);
            loginConfiguration.failureHandler(securityEventHandler);

        });
        http.userDetailsService(userDetailService);
        http.authorizeHttpRequests(
                req -> {
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/frontoffice/publishApi"))
                            .permitAll();
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/frontoffice/mockException"))
                            .permitAll();
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/frontoffice/publisher/role"))
                            .hasRole(Role.PUBLISHER.name());
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/frontoffice/writer/role"))
                            .hasRole(Role.WRITER.name());
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/frontoffice/reader/role"))
                            .hasRole(Role.READER.name());
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/frontoffice/writerPublisher/role"))
                            .hasAnyRole(Role.WRITER.name(), Role.PUBLISHER.name());

                    req.anyRequest().authenticated();
                }
        );

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(preSecurityCheckJsonWebToken, UsernamePasswordAuthenticationFilter.class);
        http.cors(corsConfiguration -> corsConfiguration.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()));
        http.exceptionHandling(exceptionConfiguration -> exceptionConfiguration.authenticationEntryPoint(securityEventHandler));
        http.exceptionHandling(exceptionConfiguration -> exceptionConfiguration.accessDeniedHandler(securityEventHandler));

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
