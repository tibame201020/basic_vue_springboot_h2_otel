package com.tibame201020.backend.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tibame201020.backend.dto.CustomException;
import com.tibame201020.backend.dto.CustomUserDTO;
import com.tibame201020.backend.model.security.Auth;
import com.tibame201020.backend.util.JwtProvider;
import com.tibame201020.backend.util.OpenTelemetryUtil;
import com.tibame201020.backend.util.SecurityContextUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 處理spring-security 觸發事件
 * onAuthenticationSuccess: login success
 * onAuthenticationFailure: login fail
 * handle: 經過驗證但無對應權限
 * commence: 沒有經過驗證訪問需驗證資源
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityEventHandler implements
        AuthenticationSuccessHandler,
        AuthenticationFailureHandler,
        AccessDeniedHandler,
        AuthenticationEntryPoint {
    private final JwtProvider jwtProvider;
    private final Gson gson = new Gson();
    private final ObjectMapper objectMapper;
    private final Environment environment;

    /**
     * login success handle
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Auth auth = (Auth) authentication.getPrincipal();
        CustomUserDTO customUserDTO = CustomUserDTO.builder()
                .email(auth.getCustomUser().getEmail())
                .active(auth.getCustomUser().getActive())
                .roleList(auth.getRoleList())
                .build();

        String accessToken = jwtProvider.generateAccessToken(
                environment.getRequiredProperty("spring.application.name"),
                customUserDTO);
        log.info("login success, customUserDTO {}", customUserDTO);
        log.info("accessToken {}", accessToken);

        String ACCESS_TOKEN = "access_token";
        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, ACCESS_TOKEN);
        response.addHeader(ACCESS_TOKEN, accessToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), customUserDTO);

    }

    /**
     * login fail handle
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        log.error("login fail");
        log.error("get user {}", SecurityContextUtil.getUserInfo());
        OpenTelemetryUtil.addExceptionEvent(request, authenticationException);

        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * 處理已有Authentication用戶(已視為登入者)
     * 訪問資源無對應role時觸發
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("auth-user access deny");
        log.error("get user {}", SecurityContextUtil.getUserInfo());
        OpenTelemetryUtil.addExceptionEvent(request, accessDeniedException);


        response.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
    }

    /**
     * 處理Anonymous用戶(未登入者)
     * 訪問需要授權資源時觸發
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        log.error("un-auth user deny");
        log.error("get user {}", SecurityContextUtil.getUserInfo());
        OpenTelemetryUtil.addExceptionEvent(request, authenticationException);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
