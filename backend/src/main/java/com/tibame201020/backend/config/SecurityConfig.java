package com.tibame201020.backend.config;

import com.tibame201020.backend.constant.Role;
import com.tibame201020.backend.filter.PreSecurityCheckJsonWebToken;
import com.tibame201020.backend.handler.SecurityEventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
 * 設定spring security filter chain
 * login config: 定義login api, parameters, success, fail handle
 * request chain:white list, need role, need auth
 * exception handle
 * password encoder bean
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityEventHandler securityEventHandler;
    private final PreSecurityCheckJsonWebToken preSecurityCheckJsonWebToken;

    /**
     * security filter chain
     * close csrf
     * close session
     * login config: login api, usernameParameter, passwordParameter, successHandler, failureHandler
     * request authorize config: white list, hasRole, authenticated
     * add preSecurityCheckJsonWebToken before UsernamePasswordAuthenticationFilter: for valid jwt token
     * apply cors default setting
     * exceptionHandling: authenticationEntryPoint, accessDeniedHandler
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.formLogin(loginConfiguration -> {
            loginConfiguration.loginPage("/api/login");
            loginConfiguration.usernameParameter("account");
            loginConfiguration.passwordParameter("password");

            loginConfiguration.successHandler(securityEventHandler);
            loginConfiguration.failureHandler(securityEventHandler);
        });

        http.authorizeHttpRequests(
                req -> {
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/publishApi"))
                            .permitAll();
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/publisher/role"))
                            .hasRole(Role.PUBLISHER.name());
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/writer/role"))
                            .hasRole(Role.WRITER.name());
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/reader/role"))
                            .hasRole(Role.READER.name());
                    req.requestMatchers(new MvcRequestMatcher(introspector, "/api/writerPublisher/role"))
                            .hasAnyRole(Role.WRITER.name(), Role.PUBLISHER.name());
                    req.anyRequest().authenticated();

                }
        );

        http.addFilterBefore(preSecurityCheckJsonWebToken, UsernamePasswordAuthenticationFilter.class);
        http.cors(corsConfiguration -> corsConfiguration.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()));
        http.exceptionHandling(exceptionConfiguration -> exceptionConfiguration.authenticationEntryPoint(securityEventHandler));
        http.exceptionHandling(exceptionConfiguration -> exceptionConfiguration.accessDeniedHandler(securityEventHandler));

        return http.build();
    }

    /**
     * password encoder bean
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
