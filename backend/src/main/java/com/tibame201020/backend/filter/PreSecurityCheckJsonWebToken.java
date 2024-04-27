package com.tibame201020.backend.filter;

import com.tibame201020.backend.constant.SystemProps;
import com.tibame201020.backend.dto.CustomUserDTO;
import com.tibame201020.backend.util.JwtProvider;
import com.tibame201020.backend.util.OpenTelemetryUtil;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        OpenTelemetryUtil.addContextInfoToMDC();
        log.info("attempt api is = {} ", request.getServletPath());

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(SystemProps.BEARER)) {
            log.warn("header don't have authorization token");
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(SystemProps.BEARER.length());
        CustomUserDTO customUserDTO = jwtProvider.parseToken(token);

        authenticationUserToContext(customUserDTO);
        OpenTelemetryUtil.addContextInfoToMDC();

        filterChain.doFilter(request, response);
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
}
