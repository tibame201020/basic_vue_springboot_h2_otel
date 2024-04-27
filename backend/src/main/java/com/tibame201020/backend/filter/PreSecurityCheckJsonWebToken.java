package com.tibame201020.backend.filter;

import com.tibame201020.backend.constant.SystemProps;
import com.tibame201020.backend.dto.CustomUserDTO;
import com.tibame201020.backend.model.RequestRecord;
import com.tibame201020.backend.service.RequestRecordService;
import com.tibame201020.backend.util.JwtProvider;
import com.tibame201020.backend.util.OpenTelemetryUtil;
import com.tibame201020.backend.util.SecurityContextUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 由spring security filter chain定義在UsernamePasswordAuthenticationFilter之前
 * 檢查有無token, 驗證token, 主動添加authenticationToken至SecurityContextHolder Context
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PreSecurityCheckJsonWebToken extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final RequestRecordService requestRecordService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        OpenTelemetryUtil.addContextInfoToMDC();
        log.info("attempt api is = {} ", request.getServletPath());

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(SystemProps.BEARER)) {
            log.warn("header don't have authorization token");
            filterChain.doFilter(request, response);
            loggerRecord(request, response);
            return;
        }

        String token = authorizationHeader.substring(SystemProps.BEARER.length());
        CustomUserDTO customUserDTO = jwtProvider.parseToken(token);

        authenticationUserToContext(customUserDTO);
        OpenTelemetryUtil.addContextInfoToMDC();

        filterChain.doFilter(request, response);
        loggerRecord(request, response);
    }

    /**
     * set customUser to SecurityContextHolder Context
     */
    private void authenticationUserToContext(CustomUserDTO customUserDTO) {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                customUserDTO.getRoleList().stream()
                        .map(roleEnum -> new SimpleGrantedAuthority(
                                SystemProps.ROLE_PREFIX + roleEnum.name()
                        ))
                        .toList();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        customUserDTO,
                        null,
                        simpleGrantedAuthorities
                );

        authenticationToken.setDetails(customUserDTO);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private void loggerRecord(HttpServletRequest request, HttpServletResponse response) {
        log.debug("api {}", request.getServletPath());
        log.debug("user {}", SecurityContextUtil.getUserInfo());
        log.debug("remote ip {}", request.getRemoteAddr());

        log.debug("response.getStatus() {} ", response.getStatus());

        requestRecordService.loggingRequestRecord(
                RequestRecord.builder()
                        .requestPath(request.getRequestURI())
                        .userEmail(SecurityContextUtil.getCurrentUserEmail())
                        .traceId(OpenTelemetryUtil.getTraceId())
                        .isError(200 == response.getStatus())
                        .responseStatus(response.getStatus())
                        .recordDateTime(LocalDateTime.now())
                        .build()
        );
    }
}
